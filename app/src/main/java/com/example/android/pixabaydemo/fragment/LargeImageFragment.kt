package com.example.android.pixabaydemo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.android.pixabaydemo.R
import com.example.android.pixabaydemo.databinding.FragmentLargeImageBinding
import com.example.android.pixabaydemo.other.OnSwipeTouchListener
import com.example.android.pixabaydemo.viewModel.Communicator



class LargeImageFragment : Fragment() {

    private var _binding: FragmentLargeImageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val communicator: Communicator by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLargeImageBinding.inflate(inflater, container, false)

        context?.let {
            Glide.with(it).load(communicator.largeImageURL.value.toString())
                .centerCrop().fitCenter()
                .into(binding.largeImageView)
        }


        binding.shareBtn.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }
        binding.largeImageView.setOnTouchListener(object : OnSwipeTouchListener(context){
            @SuppressLint("ClickableViewAccessibility")
            override fun onSwipeUp() {
                activity?.onBackPressed()

            }
        })

        binding.info.setOnClickListener {
            val infoDialog = LayoutInflater.from(context).inflate(R.layout.info_dialog,null)
            val mBuilder = android.app.AlertDialog.Builder(context)
                .setView(infoDialog)
                .setTitle("Info")
            val mAlertDialog = mBuilder.show()
            infoDialog.findViewById<TextView>(R.id.height_value_txt).setText(communicator.imageHeight.value.toString())
            infoDialog.findViewById<TextView>(R.id.width_value_txt).setText(communicator.imageWidth.value.toString())
            infoDialog.findViewById<TextView>(R.id.size_value_txt).setText(communicator.imageSize.value.toString())
            infoDialog.findViewById<TextView>(R.id.downloads_value_txt).setText(communicator.downloads.value.toString())
            infoDialog.findViewById<TextView>(R.id.views_value_txt).setText(communicator.views.value.toString())
            hideSystemUI()
        }


        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity?.getWindow()?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}