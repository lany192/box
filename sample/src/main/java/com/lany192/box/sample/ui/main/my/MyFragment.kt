package com.lany192.box.sample.ui.main.my

import com.alibaba.android.arouter.SampleRouter
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.dialog.BirthdayDialog
import com.github.lany192.dialog.MenuDialog
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extensions.load
import com.hjq.toast.Toaster
import com.lany192.box.network.data.bean.UserInfo
import com.lany192.box.router.provider.HelloProvider
import com.lany192.box.router.provider.LoginProvider
import com.lany192.box.sample.R
import com.lany192.box.sample.databinding.FragmentMyBinding
import com.lany192.box.sample.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import github.leavesczy.matisse.GlideImageEngine
import github.leavesczy.matisse.Matisse
import github.leavesczy.matisse.MatisseCapture
import github.leavesczy.matisse.MatisseCaptureContract
import github.leavesczy.matisse.MatisseContract
import github.leavesczy.matisse.MediaResource
import github.leavesczy.matisse.MediaStoreCaptureStrategy
import github.leavesczy.matisse.MediaType
import java.time.LocalDate


@AndroidEntryPoint
@Route(path = "/page/my")
class MyFragment : VMVBFragment<MyViewModel, FragmentMyBinding>() {

    @Autowired
    lateinit var loginProvider: LoginProvider

    @Autowired
    lateinit var helloProvider: HelloProvider

    private lateinit var userViewModel: UserViewModel
    private val mediaPickerLauncher =
        registerForActivityResult(MatisseContract()) { result: List<MediaResource>? ->
            if (!result.isNullOrEmpty()) {
                val mediaResource = result[0]
                val uri = mediaResource.uri
                val path = mediaResource.path
                val name = mediaResource.name
                val mimeType = mediaResource.mimeType
                Toaster.show(uri)
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(MatisseCaptureContract()) { result: MediaResource? ->
            if (result != null) {
                val uri = result.uri
                val path = result.path
                val name = result.name
                val mimeType = result.mimeType
                Toaster.show(uri)
            }
        }

    override fun initImmersionBar() {
        BarUtils.init(this).init()
    }

    override fun init() {
        super.init()
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        userViewModel.userInfo.observe(this) { userInfo: UserInfo -> binding.testView.hint(userInfo.name) }
        binding.downloadView.setOnClickListener { SampleRouter.startDownload() }
        binding.dialogView.setOnClickListener { showDialog() }
        binding.loginView.setOnClickListener {
            loginProvider.startLogin()
        }
        binding.settingsView.setOnClickListener { SampleRouter.startSettings() }
        binding.helloView.setOnClickListener {
            helloProvider.startHello()
        }
        binding.goods.setOnClickListener { SampleRouter.startGoods() }
        binding.dialog2View.setOnClickListener { showDialog2() }
        binding.birthday.setOnClickListener {
            BirthdayDialog(LocalDate.of(2001, 1, 2)).show()
        }
        binding.testView.setOnClickListener {
            userViewModel.setName("我是张三")
        }
        binding.image.load(R.mipmap.a)
        binding.gif1.load("https://img.zcool.cn/community/01ef345bcd8977a8012099c82483d3.gif")
        binding.gif2.load("https://img.zcool.cn/community/01b8355bcd8978a801213deaae9e9c.gif")
        binding.checkView.setOnCheckChangeListener { Toaster.show(it) }
        binding.checkView.isChecked = true
        binding.imagePicker.setOnClickListener {
            mediaPickerLauncher.launch(
                Matisse(
                    maxSelectable = 1,
                    imageEngine = GlideImageEngine(),
                    mediaType = MediaType.ImageOnly
                )
            )
        }
        binding.photoPicker.setOnClickListener {
            takePictureLauncher.launch(MatisseCapture(captureStrategy = MediaStoreCaptureStrategy()))
        }
        binding.video.setOnClickListener { SampleRouter.startVideo() }
        binding.test1.setOnClickListener {
            helloProvider.startHello()
        }
        binding.test2.setOnClickListener {
            loginProvider.startLogin()
        }
        binding.roundLayout.setRadius(30f)
    }

    private fun showDialog2() {
        val menus = mutableListOf<String>()
        for (i in 0..4) {
            menus.add("test$i")
        }
        val dialog = MenuDialog()
        dialog.setTitle("我是标题")
        dialog.setItems(menus)
        dialog.show()
    }

    private fun showDialog() {
        val dialog = SimpleDialog()
        dialog.setTitle("提示")
        dialog.setMessage("猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁")
        dialog.setRightButton("确定") { }
        dialog.setLeftButton("取消") { }
        dialog.show()
    }
}