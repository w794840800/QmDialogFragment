package zs.qimai.com.viewtest

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*

abstract class QmBaseDialogFragment : DialogFragment() {

    abstract fun getLayoutId(): Int
    abstract fun bindView(v: View)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setStyle(STYLE_NO_TITLE,R.style.dIALOG)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(getLayoutId(), container, false)
        // return super.onCreateView(inflater, container, savedInstanceState)
        bindView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = dialog
        dialog.requestWindowFeature(STYLE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(isCanCanceldOnTouch())

    }

    override fun onStart() {
        super.onStart()
        val windowManager = dialog.window
        //设置宽高
        val layoutParams = windowManager.attributes
        layoutParams.width = getDialogWidth()
        layoutParams.height = getDialogHeight()
        //设置gravity
        layoutParams.gravity = getGravity()
        windowManager.attributes = layoutParams

    }



     open fun getGravity() = Gravity.CENTER

     open fun getDialogWidth() = WindowManager.LayoutParams.WRAP_CONTENT
     open fun getDialogHeight() = WindowManager.LayoutParams.WRAP_CONTENT

    open fun isCanCanceldOnTouch() = true

}