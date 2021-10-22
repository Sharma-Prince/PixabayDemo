package com.example.android.pixabaydemo.fragment

import android.annotation.SuppressLint

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.android.volley.Request.*

import com.android.volley.toolbox.JsonObjectRequest
import com.example.android.pixabaydemo.adapter.StaggeredImageAdapter
import com.example.android.pixabaydemo.other.VolleySingleton

import com.example.android.pixabaydemo.databinding.FragmentStaggeredImageBinding
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.android.pixabaydemo.BuildConfig
import com.example.android.pixabaydemo.R
import com.example.android.pixabaydemo.adapter.ImageClickListener
import com.example.android.pixabaydemo.database.PxImage
import com.example.android.pixabaydemo.viewModel.Communicator
import com.example.android.pixabaydemo.viewModel.ImageViewModel
import org.json.JSONArray
import org.json.JSONObject


class StaggeredImageFragment : Fragment(), ImageClickListener {

    private var _binding: FragmentStaggeredImageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var imageItems: MutableList<PxImage>? = null
    private var adapter : StaggeredImageAdapter? = null
    private var mActivity: Activity? = null

    private var imageViewModel: ImageViewModel?=null
    private val communicator: Communicator by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStaggeredImageBinding.inflate(inflater, container, false)

        mActivity = this.activity

        val application = requireNotNull(this.activity).application


        imageItems = mutableListOf()


        adapter = context?.let { StaggeredImageAdapter(it,this) }

        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        imageViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ImageViewModel::class.java)

        imageViewModel!!.allImage.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter?.updateList(it)
            }
        })

        binding.editText.setOnEditorActionListener() { editTxt, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                if(editTxt.text.isNotEmpty()){
                    editTxt.hideKeyboard()
                    val txt = editTxt.text.toString().replace(" ","+")
                    getDataFromAPI(txt)
                }
                true
            }else{
                false
            }
        }

        val txt = communicator.search.value.toString()
        if(txt.isNotEmpty()){
            binding.editText.setText(txt)
            binding.editText.hideKeyboard()
            getDataFromAPI(txt)

        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("NotifyDataSetChanged")
    fun getDataFromAPI(txt : String){
        imageViewModel?.deleteNode()
        val url = "https://pixabay.com/api/?key=${BuildConfig.PX_KEY}&q=${txt}s&image_type=photo"
        val jsonObjectRequest = JsonObjectRequest(
            Method.GET, url, null,
            { response ->
                val jsonArray: JSONArray = response.getJSONArray("hits")
                val x = jsonArray.length()
                Log.i("arr","$x")
                for (i in 0 until jsonArray.length()){
                val jsonObject: JSONObject = jsonArray[i] as JSONObject
                    val item = PxImage()
                    item.previewURL = jsonObject.getString("previewURL")
                    item.largeImageURL = jsonObject.getString("largeImageURL")
                    item.imageHeight = jsonObject.getString("imageHeight")
                    item.imageWidth = jsonObject.getString("imageWidth")
                    item.imageSize = jsonObject.getString("imageSize")
                    item.downloads = jsonObject.getString("downloads")
                    item.likes = jsonObject.getString("likes")
                    item.views = jsonObject.getString("views")
                    imageViewModel?.insertNode(item)
                }
                binding.recyclerView.adapter!!.notifyDataSetChanged()

            },
            {
                // TODO: Handle error
            }
        )
        if (mActivity != null) {
            VolleySingleton.getInstance(mActivity!!).addToRequestQueue(jsonObjectRequest)
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onItemCLicked(pxImage: PxImage) {
        communicator.largeImageURL.value = pxImage.largeImageURL
        communicator.imageHeight.value = pxImage.imageHeight
        communicator.imageWidth.value = pxImage.imageWidth
        communicator.downloads.value = pxImage.downloads
        communicator.imageSize.value = pxImage.imageSize
        communicator.views.value = pxImage.views
        Log.i("data",communicator.largeImageURL.value.toString())
        view?.let { Navigation.findNavController(it).navigate(R.id.action_staggeredImageFragment_to_largeImageFragment) }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
