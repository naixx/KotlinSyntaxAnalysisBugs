package me.packbag.android.ui.adapters;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.github.naixx.BaseAdapter;
import com.github.naixx.BaseViewHolder;
import com.github.naixx.Bus;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import butterknife.Bind;
import me.packbag.android.R;
import me.packbag.android.db.model.ItemCategory;
import me.packbag.android.db.model.ItemInSet;
import me.packbag.android.db.model.ItemStatus;
import me.packbag.android.ui.events.ItemStatusChangedEvent;

/**
 * Created by astra on 17.07.2015.
 */
public class ItemsAdapter extends BaseAdapter<ItemInSet, ItemsAdapter.ViewHolder> implements StickyRecyclerHeadersAdapter<ItemsAdapter.HeaderViewHolder> {

    public ItemsAdapter(AdapterCustomizer customizer) {this.customizer = customizer;}

    interface AdapterCustomizer {

        int getPopupMenuRes();

        int getListItemRes();
    }

    class ViewHolder extends BaseViewHolder<ItemInSet> {

        @Bind(R.id.name)    TextView name;
        @Bind(R.id.moreBtn) View     moreBtn;

        @Bind(R.id.takeBtn)
        @Nullable
        View takeBtn;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(ItemInSet item, int position) {
            name.setText(item.getItem().getName());
            if (takeBtn != null) {
                takeBtn.setOnClickListener(v -> Bus.post(new ItemStatusChangedEvent(item, ItemStatus.TAKEN)));
            }
            moreBtn.setOnClickListener(v -> showPopup(v, item));
        }

        private void showPopup(View v, ItemInSet item) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.inflate(customizer.getPopupMenuRes());
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_popup_useless:
                        Bus.post(new ItemStatusChangedEvent(item, ItemStatus.USELESS));
                        return true;
                    case R.id.action_popup_back_to_bag:
                        Bus.post(new ItemStatusChangedEvent(item, ItemStatus.CURRENT));
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        }
    }

    private final AdapterCustomizer customizer;

    class HeaderViewHolder extends BaseViewHolder<String> {

        @Bind(android.R.id.text1) TextView name;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(String item, int position) {
            name.setText(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(customizer.getListItemRes(), parent, false));
    }

    @Override
    public long getHeaderId(int i) {
        return items.get(i).getItem().getCategory().getId() + 2; //we add 2 because we have default category with id -1
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder headerViewHolder, int position) {
        ItemCategory cat = items.get(position).getItem().getCategory();
        if (cat.getId() == -1) {
            headerViewHolder.bind(headerViewHolder.itemView.getContext().getString(R.string.list_item_category_user),
                    position);
        } else {
            headerViewHolder.bind(cat.getName(), position);
        }
    }
}
