package me.packbag.android.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration

import net.tribe7.common.collect.FluentIterable

import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.FragmentArg
import org.androidannotations.annotations.OptionsMenu
import org.androidannotations.annotations.ViewById

import me.packbag.android.R
import me.packbag.android.db.model.ItemInSet
import me.packbag.android.db.model.ItemStatus
import me.packbag.android.ui.ItemProvider
import me.packbag.android.ui.adapters.Customizers
import me.packbag.android.ui.adapters.ItemsAdapter
import rx.Observable
import rx.Subscription

@EFragment(R.layout.fragment_itemlist)
@OptionsMenu(R.menu.fragment_list_item)
open class ItemListFragment : Fragment() {

    @ViewById lateinit var recyclerView: RecyclerView
    @FragmentArg lateinit var status: ItemStatus

    private var adapter: ItemsAdapter? = null

    @AfterViews
    internal fun afterViews() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(StickyRecyclerHeadersDecoration(adapter))
        loadAllItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        val searchView = MenuItemCompat.getActionView(menu!!.findItem(R.id.action_search)) as SearchView

        val textChanges = RxSearchView.queryTextChanges(searchView).map<String>({ it -> it.toString().toLowerCase().trim({ it <= ' ' }) })
        val s = Observable.combineLatest<String, List<ItemInSet>, ImmutableList<ItemInSet>>(textChanges,
                items
        ) { text, items -> FluentIterable.from(items).filter { item: ItemInSet -> item.getItem().getName().toLowerCase().contains(text) }.toList() }.subscribe(Action1<ImmutableList<ItemInSet>> { adapter!!.swapItems(it) })

        searchView.setOnCloseListener {
            s.unsubscribe()
            loadAllItems()
            false
        }
    }

    private val items: Observable<List<ItemInSet>>
        get() = (context as ItemProvider).getItems(status)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ItemsAdapter(Customizers.create(status))
    }

    private fun loadAllItems() {
        items.subscribe(Action1<List<ItemInSet>> { adapter!!.swapItems(it) })
    }
}
