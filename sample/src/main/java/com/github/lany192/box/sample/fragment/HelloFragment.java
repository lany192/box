package com.github.lany192.box.sample.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.delegate.Divider;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.PicBean;
import com.github.lany192.box.sample.delegate.HelloDelegate;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.box.utils.JsonUtils;
import com.github.lany192.box.widget.ShowView;
import com.github.lany192.decoration.LinearDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelloFragment extends BaseFragment {
    @BindView(R.id.showView)
    ShowView mShowView;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.hello)
                .toolBarLayoutId(R.layout.toolbar_hello);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mShowView.setLayoutManager(manager);
        mShowView.addItemDecoration(new LinearDecoration(manager.getOrientation())
                .setPadding(DensityUtils.dp2px(8))
                .setColor(Color.GRAY)
                .setWidth(1));
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


        PicBean bean = new PicBean();
        bean.setCode(1);
        bean.setMsg("请求成功");
        bean.setData(pics);

        log.i(JsonUtils.object2json(bean));

        List<ItemDelegate> items = new ArrayList<>();
        for (String pic : pics) {
            items.add(new HelloDelegate(pic));
            items.add(new Divider()
                    .color(Color.WHITE)
                    .hintColor(Color.BLACK)
                    .hint("华丽的分割线----------------------------"));
        }
        mShowView.setAdapter(new MultiAdapter(items));
    }
}
