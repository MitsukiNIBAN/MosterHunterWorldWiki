package com.satou.wiki.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.satou.wiki.R;
import com.satou.wiki.data.entity.Buki;
import com.satou.wiki.data.entity.Sharpness;

/**
 * Created by Mitsuki on 2018/4/19.
 */

public class BukiAdapter extends BaseAdapter {
    private Activity activity;
    private Buki buki;
    private final int TYPECOUNT = 4;
    public static final int HEADER = 0;
    public static final int TITLE = 1;
    public static final int CONTENT = 2;
    public static final int EXTENDS = 3;

    public BukiAdapter(Activity ctx) {
        activity = ctx;
    }

    @Override
    public int getCount() {
        if (buki == null)
            return 0;
        else if (buki.getDataList() == null) {
            return 2;
        } else {
            return 2 + buki.getDataList().size();
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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else if (position == 1) {
            return EXTENDS;
        } else {
            return buki.getDataList().get(position - 2).getItemType();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (getItemViewType(i)) {
            case HEADER:
                return handleGetHeaderView(i, view, viewGroup);
            case EXTENDS:
                return handleGetExtendView(i, view, viewGroup);
            case TITLE:
                return handleGetTitleView(i, view, viewGroup);
            case CONTENT:
                return handleGetContentView(i, view, viewGroup);
            default:
                return null;
        }
    }

    public void setBuki(Buki b) {
        buki = b;
        notifyDataSetChanged();
    }

    private class HeaderViewHolder {
        public TextView nameView;
        public TextView rareView;
        public TextView setView;
        public TextView attackView;
        public TextView critView;
        public TextView defenseView;
        public TextView attrView;

        public HeaderViewHolder(View viewRoot) {
            nameView = viewRoot.findViewById(R.id.tv_name);
            rareView = viewRoot.findViewById(R.id.tv_rare);
            setView = viewRoot.findViewById(R.id.tv_set);
            attackView = viewRoot.findViewById(R.id.tv_attack);
            critView = viewRoot.findViewById(R.id.tv_crit);
            defenseView = viewRoot.findViewById(R.id.tv_defense);
            attrView = viewRoot.findViewById(R.id.tv_attr);
        }
    }

    private View handleGetHeaderView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_buki_header, viewGroup, false);
            convertView.setTag(new HeaderViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof HeaderViewHolder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) convertView.getTag();
            if (buki != null) {
                headerViewHolder.nameView.setText(buki.getName());
                headerViewHolder.rareView.setText(buki.getRare());
                headerViewHolder.setView.setText(buki.getSet());
                headerViewHolder.attackView.setText(buki.getAttack());
                headerViewHolder.critView.setText(buki.getCrit());
                headerViewHolder.defenseView.setText(buki.getDefense());
                headerViewHolder.attrView.setText(buki.getAttr());
            }
        }
        return convertView;
    }

    private class TitleViewHolder {
        public TextView title;

        public TitleViewHolder(View viewRoot) {
            title = viewRoot.findViewById(R.id.tv_title);
        }
    }

    private View handleGetTitleView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_all_title, viewGroup, false);
            convertView.setTag(new TitleViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof TitleViewHolder) {
            final TitleViewHolder titleViewHolder = (TitleViewHolder) convertView.getTag();
            if (buki != null && buki.getDataList() != null) {
                titleViewHolder.title.setText(buki.getDataList().get(position - 2).getContent());
            }
        }
        return convertView;
    }

    private class ContentViewHolder {
        public TextView content;

        public ContentViewHolder(View viewRoot) {
            content = viewRoot.findViewById(R.id.tv_content);
        }
    }

    private View handleGetContentView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_all_content, viewGroup, false);
            convertView.setTag(new ContentViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof ContentViewHolder) {
            final ContentViewHolder contentViewHolder = (ContentViewHolder) convertView.getTag();
            if (buki != null && buki.getDataList() != null) {
                contentViewHolder.content.setText(buki.getDataList().get(position - 2).getContent());
            }
        }
        return convertView;
    }

    private class ExtendViewHolder {
        public LinearLayout bugLayout;
        public TextView bugView;
        public LinearLayout bottleLayout;
        public TextView bottleView;
        public LinearLayout shellingLayout;
        public TextView shellingView;
        public LinearLayout timbreLayout;
        public TextView timbreView;
        public TextView bulletView;
        public LinearLayout sharpnessLayout;

        public ExtendViewHolder(View viewRoot) {
            bugLayout = viewRoot.findViewById(R.id.ll_bug);
            bugView = viewRoot.findViewById(R.id.tv_bug);
            bottleLayout = viewRoot.findViewById(R.id.ll_bottle);
            bottleView = viewRoot.findViewById(R.id.tv_bottle);
            shellingLayout = viewRoot.findViewById(R.id.ll_shelling);
            shellingView = viewRoot.findViewById(R.id.tv_shelling);
            timbreLayout = viewRoot.findViewById(R.id.ll_timbre);
            timbreView = viewRoot.findViewById(R.id.tv_timbre);
            bulletView = viewRoot.findViewById(R.id.tv_bullet);

            sharpnessLayout = viewRoot.findViewById(R.id.ll_sharpness);
        }
    }

    private View handleGetExtendView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_buki_extend_info, viewGroup, false);
            convertView.setTag(new ExtendViewHolder(convertView));
        }
        if (convertView != null && convertView.getTag() instanceof ExtendViewHolder) {
            final ExtendViewHolder extendViewHolder = (ExtendViewHolder) convertView.getTag();
            if (buki != null) {
                if (buki.getBullet().equals("null")) {
                    extendViewHolder.bulletView.setVisibility(View.GONE);
                } else {
                    extendViewHolder.bulletView.setVisibility(View.VISIBLE);
                }
                if (buki.getBug().equals("null")) {
                    extendViewHolder.bugLayout.setVisibility(View.GONE);
                } else {
                    extendViewHolder.bugLayout.setVisibility(View.VISIBLE);
                    extendViewHolder.bugView.setText(buki.getBug());
                }
                if (buki.getBottle().equals("null")) {
                    extendViewHolder.bottleLayout.setVisibility(View.GONE);
                } else {
                    extendViewHolder.bottleLayout.setVisibility(View.VISIBLE);
                    extendViewHolder.bottleView.setText(buki.getBottle());
                }
                if (buki.getTimbre().equals("null")) {
                    extendViewHolder.timbreLayout.setVisibility(View.GONE);
                } else {
                    extendViewHolder.timbreLayout.setVisibility(View.VISIBLE);
                    extendViewHolder.timbreView.setText(buki.getTimbre());
                }
                if (buki.getShelling().equals("null")) {
                    extendViewHolder.shellingLayout.setVisibility(View.GONE);
                } else {
                    extendViewHolder.shellingLayout.setVisibility(View.VISIBLE);
                    extendViewHolder.shellingView.setText(buki.getShelling());
                }

                if (buki.getSharpness() == null || buki.getSharpness().size() <= 0) {
                    extendViewHolder.sharpnessLayout.setVisibility(View.GONE);
                } else {
                    extendViewHolder.sharpnessLayout.setVisibility(View.VISIBLE);
                    for (Sharpness sharpness : buki.getSharpness()) {
                        View view = activity.getLayoutInflater().inflate(R.layout.item_sharpness, null);
                        View redView = view.findViewById(R.id.view_level_red);
                        View orangeView = view.findViewById(R.id.view_level_orange);
                        View yellowView = view.findViewById(R.id.view_level_yellow);
                        View greenView = view.findViewById(R.id.view_level_green);
                        View buleView = view.findViewById(R.id.view_level_blue);
                        View whiteView = view.findViewById(R.id.view_level_white);
                        View purplrView = view.findViewById(R.id.view_level_purple);
                        TextView nameView = view.findViewById(R.id.tv_sharpness_name);
                        nameView.setText(sharpness.getName() + "");
                        String[] sh = sharpness.getSharpness().split(",");
//                        Log.e("sharpness", sharpness.getSharpness());
                        redView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[0].substring(7, sh[0].length() - 3)));
                        orangeView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[1].substring(7, sh[1].length() - 3)));
                        yellowView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[2].substring(7, sh[2].length() - 3)));
                        greenView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[3].substring(7, sh[3].length() - 3)));
                        buleView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[4].substring(7, sh[4].length() - 3)));
                        whiteView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[5].substring(7, sh[5].length() - 3)));
                        purplrView.getLayoutParams().width = dip2px(
                                Float.parseFloat(sh[6].substring(7, sh[6].length() - 3)));
                        extendViewHolder.sharpnessLayout.addView(view);
                    }
                }
            }
        }
        return convertView;
    }

    public int dip2px(float dipValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
