package zs.qimai.com.viewtest

import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.TextView

class QmDialogFragment : QmBaseDialogFragment() {
    lateinit var dialogController: DialogController

    init {
        dialogController = DialogController()
    }

    override fun getLayoutId() = dialogController.layoutResId

    override fun bindView(v: View) {
        //绑定监听
        if (dialogController != null && dialogController.ids != null && dialogController.ids!!.size > 0) {

            for (id in dialogController.ids!!) {
                getDialogChildView<View>(id, v)?.let {
                    it.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            if (dialogController.onViewClickListener != null) {
                                dialogController.onViewClickListener!!.onViewClick(v!!, this@QmDialogFragment)
                            }
                        }
                    })
                }
            }
        }
        //设置文字
        if (dialogController.paramsMap != null && dialogController.paramsMap!!.isNotEmpty()) {
            for (item in dialogController.paramsMap!!) {

                getDialogChildView<TextView>(item.key, v)?.let {
                    it.text = item.value
                }
            }
        }
    }


    fun <T : View> getDialogChildView(id: Int, v: View): T? {
        val childView: View? = v.findViewById(id)
        return if (childView == null) null else childView as T
    }

    override fun getGravity(): Int {
        return dialogController.gravity
    }

    override fun getDialogWidth(): Int {
        return dialogController.width
    }

    override fun getDialogHeight(): Int {
        return dialogController.height
    }

    class Builder(var fragmentManager: FragmentManager) {
        var params: DialogController.Params
        var paramsMap: MutableMap<Int, String>? = null

        init {
            params = DialogController.Params()
            params.fragmentManager = this.fragmentManager
        }

        fun setGravity(gravity: Int): Builder {
            params.gravity = gravity
            return this
        }

        fun setWidth(width: Int): Builder {
            params.width = width
            return this
        }

        fun setHeight(height: Int): Builder {
            params.height = height
            return this
        }

        fun setLayoutRes(layout: Int): Builder {
            params.layoutResId = layout
            return this
        }

        fun addViewClickId(vararg ids: Int): Builder {
            params.ids = ids
            return this
        }

        fun create(): QmDialogFragment {
            val qmDialogFragment = QmDialogFragment()
            params.apply(qmDialogFragment.dialogController)
            return qmDialogFragment
        }

        fun setViewClick(onViewClickListener: OnViewClickListener): Builder {
            params.onViewClickListener = onViewClickListener
            return this
        }

        fun setText(id: Int, text: String): Builder {

            if (paramsMap == null) {
                paramsMap = mutableMapOf()
            }
            //paramsMap!!.put(id,text)
            params.paramsMap[id] = text
            return this
        }
    }

    fun show(): QmDialogFragment {
        dialogController.fragmentManager.beginTransaction()
                .add(this, "test")
                .commitAllowingStateLoss()
        //   show(fragmentManager)

        return this
    }

}