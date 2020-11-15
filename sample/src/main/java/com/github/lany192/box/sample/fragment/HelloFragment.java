package com.github.lany192.box.sample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.delegate.DividerDelegate;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.delegate.HelloDelegate;
import com.github.lany192.box.utils.JsonUtils;
import com.github.lany192.box.widget.ItemsView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class HelloFragment extends BaseFragment {
    @BindView(R.id.ItemsView)
    ItemsView mItemsView;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return new FragmentConfig().layoutId(R.layout.hello)
                .toolBarLayoutId(R.layout.toolbar_hello);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mItemsView.setLayoutManager(manager);
//        mItemsView.addItemDecoration(new LinearDecoration(manager.getOrientation())
//                .setPadding(DensityUtils.dp2px(8))
//                .setColor(Color.GRAY)
//                .setWidth(1));
        List<String> images = new ArrayList<>();
        images.add("https://taoduorou.cn/files/images/2019-09-27/0bc183f272e366fc0415cbe83d2ba08e.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-18/8d6ac435c042e812544429cc5a9dee59.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-19/7d7a93f3551587c031b142b2c407d0e6.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-19/8d6ac435c042e812544429cc5a9dee59.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-19/944938478f31f159f69ee43d76bf51e1.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-19/d12ea67dbae1f31f6cd8516538e7ca60.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-20/7d7a93f3551587c031b142b2c407d0e6.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-20/944938478f31f159f69ee43d76bf51e1.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-24/7d7a93f3551587c031b142b2c407d0e6.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-26/7d7a93f3551587c031b142b2c407d0e6.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-26/8d6ac435c042e812544429cc5a9dee59.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-26/944938478f31f159f69ee43d76bf51e1.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-26/d12ea67dbae1f31f6cd8516538e7ca60.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-31/2d68ecfc3d0f20b24fbd1fb63a9ae122.jpg");
        images.add("https://taoduorou.cn/files/images/2019-10-31/a25b734b8c6adf21a67c8eb8b6548dcd.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-02/1448fd619b5ab68acb89cfa49b062b41.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-02/2d68ecfc3d0f20b24fbd1fb63a9ae122.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-02/434d6f73d4054fc16531fede52e71af7.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-02/474334ffd4352efbdd32bc650be5faee.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-02/4d00ecec6b70f59d88b4647b5793b8fc.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-02/7c9f6e2ed3914d39228047ee2a9002d4.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-04/124615564fe6a2ba36e47e97174216e0.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-04/1448fd619b5ab68acb89cfa49b062b41.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-04/62fc51772a0de2a5aa3dca0ece0fd08e.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-04/7d81938bab814f0f5d7491bc02538d6f.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-05/2d68ecfc3d0f20b24fbd1fb63a9ae122.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-05/573b8e48b2e65d3e34290c537591d64b.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-05/9f3e6637197c89ef3af86c5507e8b8e0.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-05/a05f92780d8c3e434961fb899072381d.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-05/c696d903eebe05486b68e38768f768a4.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-07/474334ffd4352efbdd32bc650be5faee.jpg");
        images.add("https://taoduorou.cn/files/images/2019-11-07/4d00ecec6b70f59d88b4647b5793b8fc.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-06/7d7a93f3551587c031b142b2c407d0e6.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-08/47bfb9e8fdc96f7cd1d4683ad1e8cf5d.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-08/84383a9d01dd21b037a54fac4c79b6ba.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-08/c33de7eee0f7121a68ab497a5bcbddae.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-08/ca4c00e50abfe0cccd9024252b5c1875.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-08/e33d9d792b11c68f73da004bffb14325.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-09/84383a9d01dd21b037a54fac4c79b6ba.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-10/124615564fe6a2ba36e47e97174216e0.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-10/7d81938bab814f0f5d7491bc02538d6f.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-20/16cfb93741a6061c57c485ebdfa0638a.jpg");
        images.add("https://taoduorou.cn/files/images/2019-12-20/9f3e6637197c89ef3af86c5507e8b8e0.jpg");

        log.json(JsonUtils.object2json(images));
        List<Delegate> items = images.stream().map(HelloDelegate::new).collect(Collectors.toList());
        items.add(new DividerDelegate());
        mItemsView.setAdapter(new MultiAdapter(items));
    }

}
