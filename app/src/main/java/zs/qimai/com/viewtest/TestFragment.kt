package zs.qimai.com.viewtest

import android.os.Bundle
import android.support.v4.app.Fragment

class TestFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {


        fun getInstance(bundle: Bundle): TestFragment {
//Bundle bundle = new Bundle();
            return TestFragment()

        }
    }
}
