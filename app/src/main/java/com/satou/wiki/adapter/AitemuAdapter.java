package com.satou.wiki.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.satou.wiki.R;
import com.satou.wiki.data.entity.Aitemu;

/**
 * Created by Mitsuki on 2018/4/19.
 */

public class AitemuAdapter extends BaseAdapter {
    private Activity activity;
    private Aitemu aitemu;

    private final int TYPECOUNT = 3;
    public static final int HEADER = 0;
    public static final int TITLE = 1;
    public static final int CONTENT = 2;

    public AitemuAdapter(Activity ctx) {
        activity = ctx;
    }

    @Override
    public int getCount() {
        if (aitemu == null)
            return 0;
        else {
            if (aitemu.getData() == null) {
                return 1;
            } else {
                return 1 + aitemu.getData().size();
            }
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return TYPECOUNT;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return aitemu.getData().get(position - 1).getItemType();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (getItemViewType(i)) {
            case HEADER:
                return handleGetHeaderView(i, view, viewGroup);
            case TITLE:
                return handleGetTitleView(i, view, viewGroup);
            case CONTENT:
                return handleGetContentView(i, view, viewGroup);
            default:
                return null;
        }
    }

    public void setData(Aitemu a){
        aitemu = a;
        notifyDataSetChanged();
    }

    private class HeaderViewHolder {
        public TextView nameView;
        public TextView rareView;
        public TextView carryView;
        public TextView priceView;
        public TextView infoView;

        public HeaderViewHolder(View viewRoot) {
            nameView = viewRoot.findViewById(R.id.tv_name);
            rareView = viewRoot.findViewById(R.id.tv_rare);
            carryView = viewRoot.findViewById(R.id.tv_carry);
            priceView = viewRoot.findViewById(R.id.tv_price);
            infoView = viewRoot.findViewById(R.id.tv_info);
        }
    }

    private class TitleViewHolder {
        public TextView title;

        public TitleViewHolder(View viewRoot) {
            title = viewRoot.findViewById(R.id.tv_title);
        }
    }

    private class ContentViewHolder {
        public TextView content;

        public ContentViewHolder(View viewRoot) {
            content = viewRoot.findViewById(R.id.tv_content);
        }
    }

    private View handleGetHeaderView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_aitemu_header, viewGroup, false);
            convertView.setTag(new HeaderViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof HeaderViewHolder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) convertView.getTag();
            if (aitemu != null) {
                headerViewHolder.nameView.setText(aitemu.getName());
                headerViewHolder.rareView.setText(aitemu.getRare());
                headerViewHolder.carryView.setText(aitemu.getCarry());
                headerViewHolder.priceView.setText(aitemu.getPrice());
                headerViewHolder.infoView.setText(aitemu.getInfo());
            }
        }
        return convertView;
    }

    private View handleGetTitleView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_all_title, viewGroup, false);
            convertView.setTag(new TitleViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof TitleViewHolder) {
            final TitleViewHolder titleViewHolder = (TitleViewHolder) convertView.getTag();
            if (aitemu != null && aitemu.getData() != null) {
                titleViewHolder.title.setText(aitemu.getData().get(position - 1).getContent());
            }
        }
        return convertView;
    }

    private View handleGetContentView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_all_content, viewGroup, false);
            convertView.setTag(new ContentViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof ContentViewHolder) {
            final ContentViewHolder contentViewHolder = (ContentViewHolder) convertView.getTag();
            if (aitemu != null && aitemu.getData() != null) {
                contentViewHolder.content.setText(aitemu.getData().get(position - 1).getContent());
            }
        }
        return convertView;
    }

}
