package com.github.kongpf8848.animation.activity.lottie;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.kongpf8848.animation.R;
import com.github.kongpf8848.animation.activity.BaseActivity;
import com.github.kongpf8848.animation.fragment.TitleFragment;
import com.github.kongpf8848.animation.widget.AnimationRadioView;
import com.github.kongpf8848.xsdk.ui.adapter.FragmentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class BossActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;

    private static final String[] CHANNELS = new String[]{"职位", "发现", "消息","我的"};
    private static final String[] ANIMATIONS = new String[]{"lottie_position.json", "lottie_company.json","lottie_message.json","lottie_me.json"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottie_boss;
    }

    protected void initData() {
        try {
            super.initData();
            initViewPager();
            initMagicIndicator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPager(){
        List<Fragment>fragmentList=new ArrayList<>();
        for(int i=0;i<CHANNELS.length;i++){
            TitleFragment fragment=new TitleFragment();
            Bundle bundle=new Bundle();
            bundle.putString("title",CHANNELS[i]);
            fragment.setArguments(bundle);

            fragmentList.add(fragment);
        }
        FragmentAdapter adapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList,Arrays.asList(CHANNELS));
        viewPager.setAdapter(adapter);
    }

    private void initMagicIndicator() {

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);

                View customLayout = LayoutInflater.from(context).inflate(R.layout.item_tab_boss, null);
                final AnimationRadioView img =customLayout.findViewById(R.id.item_tab_image);
                final TextView text =customLayout.findViewById(R.id.item_tab_text);
                img.setAnimation(ANIMATIONS[index]);
                text.setText(CHANNELS[index]);
                commonPagerTitleView.setContentView(customLayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

                    @Override
                    public void onSelected(int index, int totalCount) {
                        img.setChecked(true);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        img.setChecked(false);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


}
