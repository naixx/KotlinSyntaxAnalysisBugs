package me.packbag.android.ui.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

import com.github.naixx.Bus
import com.github.naixx.L

import net.tribe7.common.collect.FluentIterable
import net.tribe7.common.collect.Ordering

import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.Extra
import org.androidannotations.annotations.OnActivityResult
import org.androidannotations.annotations.OptionsItem
import org.androidannotations.annotations.OptionsMenu
import org.androidannotations.annotations.ViewById

import javax.inject.Inject

import me.packbag.android.App
import me.packbag.android.R
import me.packbag.android.db.api.IDao
import me.packbag.android.db.model.ItemInSet
import me.packbag.android.db.model.ItemSet
import me.packbag.android.db.model.ItemStatus
import me.packbag.android.ui.ItemProvider
import me.packbag.android.ui.adapters.ItemListFragmentsAdapter
import me.packbag.android.ui.events.ItemCount
import me.packbag.android.ui.events.ItemStatusChangedEvent
import rx.Observable
import rx.observables.GroupedObservable
import rx.subjects.BehaviorSubject

@EActivity(R.layout.activity_itemlist)
@OptionsMenu(R.menu.activity_item_list)
open class ItemListActivity : AppCompatActivity(), ItemProvider {
    @ViewById lateinit var tabs: TabLayout
    @ViewById lateinit var viewPager: ViewPager

    @Extra lateinit var itemSet: ItemSet
    @Inject lateinit var IDao: IDao

    private val typedItems = BehaviorSubject.create<List<ItemInSet>>()
    private val itemStatusChanged = BehaviorSubject.create(ItemStatus.TAKEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.get(this).component().inject(this)
    }

    @AfterViews
    fun afterViews() {
        title = itemSet.name

        val adapter = ItemListFragmentsAdapter(supportFragmentManager, this)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        countStatusedItemsForTitles(adapter)
        loadItems()
    }

    private fun countStatusedItemsForTitles(adapter: ItemListFragmentsAdapter) {
        val statusCounts = itemStatusChanged.flatMap<Map<ItemStatus, Int>>({ i -> //
            typedItems.flatMap<Map<ItemStatus, Int>>({ itemInSets ->
                val go = Observable.from<ItemInSet>(itemInSets).groupBy(Func1<ItemInSet, ItemStatus> { it.getStatus() })
                go.flatMap({ obs: GroupedObservable<ItemStatus, ItemInSet> -> obs.count().map<ItemCount>({ count: Int -> ItemCount(obs.key, count) }) }).toMap(Func1<ItemCount, ItemStatus> { it.getStatus() }, Func1<ItemCount, Int> { it.getCount() })
            })
        })
        itemStatusChanged //
                .filter { prev -> prev == ItemStatus.CURRENT }.flatMap<List<ItemInSet>>({ prev -> typedItems.take(1) }).map<Int>({ itemInSets -> FluentIterable.from<ItemInSet>(itemInSets).filter { it -> it.getStatus() == ItemStatus.CURRENT }.size() }).filter { count -> count === 0 }.subscribe { was -> }

        statusCounts.subscribe({ map ->
            adapter.onEvent(map)
            updateTabTitles(adapter)
        }, Action1<Throwable> { L.e(it) }, Action0 { L.i() })
    }

    private fun updateTabTitles(adapter: ItemListFragmentsAdapter) {
        for (i in 0..tabs.tabCount - 1) {
            //noinspection ConstantConditions
            tabs.getTabAt(i)!!.setText(adapter.getPageTitle(i))
        }
    }

    private fun loadItems() {
        IDao.itemsInSets(itemSet).subscribe(Action1<List<ItemInSet>> { typedItems.onNext(it) })
    }

    override fun getItems(itemStatus: ItemStatus): Observable<List<ItemInSet>> {
        return typedItems.flatMap<List<ItemInSet>>({ itemInSets: List<ItemInSet> -> Observable.from(itemInSets).filter { input -> input.status == itemStatus }.toSortedList { item, item2 -> Ordering.natural<Comparable>().onResultOf<ItemInSet> { item1: ItemInSet -> item1.getItem().getCategory().getId() }.compare(item, item2) } })
    }

    override fun onStart() {
        super.onStart()
        Bus.register(this)
    }

    override fun onStop() {
        super.onStop()
        Bus.unregister(this)
    }

    @SuppressWarnings("unused")
    fun onEvent(e: ItemStatusChangedEvent) {
        val prev = e.itemInSet.status
        e.itemInSet.setStatus(e.status).async().save()
        typedItems.take(1).subscribe(Action1<List<ItemInSet>> { typedItems.onNext(it) })
        itemStatusChanged.onNext(prev)
    }

    @OptionsItem(R.id.action_new_item)
    internal fun onNewItem() {
        //        NewItemActivity_.intent(this).itemSet(itemSet).startForResult(REQUEST_CODE_NEW_ITEM);
    }

    @OptionsItem(R.id.action_clear_items)
    internal fun onClearItems() {
        IDao.clearItems(itemSet)
        loadItems()
        scrollToFirstPage()
    }

    @OnActivityResult(REQUEST_CODE_NEW_ITEM)
    internal fun onItemAdded() {
        L.i()
        loadItems()
        scrollToFirstPage()
    }

    private fun scrollToFirstPage() {
        typedItems.take(1).subscribe { itemInSets -> viewPager.currentItem = 0 }
    }

    companion object {

        val REQUEST_CODE_NEW_ITEM = 1
    }
}
