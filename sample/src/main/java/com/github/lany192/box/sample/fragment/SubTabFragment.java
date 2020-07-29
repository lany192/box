package com.github.lany192.box.sample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Menu;
import com.github.lany192.box.sample.delegate.HelloDelegate;
import com.github.lany192.box.sample.delegate.MenuDelegate;
import com.github.lany192.box.widget.ShowView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SubTabFragment extends BaseFragment {
    @BindView(R.id.showView)
    ShowView mShowView;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_hello);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mShowView.setLayoutManager(manager);
//        mShowView.addItemDecoration(new LinearDecoration(manager.getOrientation())
//                .setPadding(DensityUtils.dp2px(8))
//                .setColor(Color.GRAY)
//                .setWidth(1));

        List<Delegate> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            items.add(new MenuDelegate(new Menu("菜单" + i, R.drawable.android)));
        }

        String[] pics = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550479961661&di=58f0ec0ba23d0bf8cff25499d8a93623&imgtype=0&src=http%3A%2F%2Fbannerdesign.cn%2Fwp-content%2Fuploads%2F2015%2F02%2F20150204014336322.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/eac4b74543a982265bd540e38782b9014b90ebda.jpg",
                "http://d.hiphotos.baidu.com/image/pic/item/4b90f603738da977625f2cf7bd51f8198718e3fe.jpg",
                "https://wx3.sinaimg.cn/bmiddle/0060lm7Tgy1g22fgqr55oj30u00k00um.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff9ge8zj30u00s777u.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhvz2z5j30k00f0mya.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff31rzuj30u00s077j.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhjkxdoj30hv0ih3zf.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fh7l9qhj30u01hc7an.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fi7pdy6j30u0140n35.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhwgxx2j30u0140djp.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhw7p7aj30u014041u.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff9oa5pj30u0140gpd.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ffkxeptj30rs0rst9k.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff37jsaj30u00l6acv.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff3ay1hj30k00plq57.jpg",
                "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fgkxa75j30u00mijwr.jpg",
                "https://wx2.sinaimg.cn/bmiddle/0060lm7Tgy1g22fidcdp4j30tr1sg7a5.jpg",
                "https://wx3.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhpsiy1j30u0140dhi.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22fflat9oj30u0140tkl.jpg",
                "https://wx2.sinaimg.cn/bmiddle/0060lm7Tgy1g22fft14xtj30u0140wqo.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ffsig5wj30u0140wqn.jpg",
                "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ffs63lhj30u0140gv0.jpg"};

        for (String pic : pics) {
            items.add(new HelloDelegate(pic));
//            items.add(new Divider()
//                    .color(Color.WHITE)
//                    .hintColor(Color.BLACK)
//                    .hint("华丽的分割线----------------------------"));
        }
        mShowView.setAdapter(new MultiAdapter(items, manager));
    }
}
