package com.example.my18application

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.my18application.databinding.FragmentVolleyBinding
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VolleyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VolleyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentVolleyBinding.inflate(inflater, container, false)
        var mutableList: MutableList<ItemVolleyModel>

        binding.btnNewsSearch.setOnClickListener {
            var keyword = binding.edtNews.text.toString()
            val url = "https://newsapi.org/v2/everything?q=${keyword}&apiKey=079dac74a5f94ebdb990ecf61c8854b7&page=1&pageSize=5"
            Log.d("mobileApp",url)
            val queue = Volley.newRequestQueue(activity)
            val jsonRequest:JsonObjectRequest = object :JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener<JSONObject> { response ->
                    mutableList = mutableListOf<ItemVolleyModel>()
                    val jsonArray : JSONArray = response.getJSONArray("articles")
                    for (i in 0 until jsonArray.length()){
                        val article = jsonArray.getJSONObject(i)
                        ItemVolleyModel().run {
                            author = article.getString("author")
                            title = article.getString("title")
                            description = article.getString("description")
                            urlToImage = article.getString("urlToImage")
                            publishedAt = article.getString("publishedAt")
                            mutableList.add(this)
                        }
                    }
                    binding.volleyRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.volleyRecyclerView.adapter = MyVolleyAdapter(activity as Context, mutableList)
                },
                Response.ErrorListener {
                    Log.d("mobileApp","error... $it")
                }
            ){
                override fun getHeaders(): MutableMap<String, String> {
                    val map = mutableMapOf<String,String>(
                        "User-agent" to "Mozilla/5.0"
                    )
                    return map
                }
            }
            queue.add(jsonRequest)
        }

        mutableList = mutableListOf<ItemVolleyModel>()
        binding.volleyRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.volleyRecyclerView.adapter = MyVolleyAdapter(activity as Context, mutableList)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VolleyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VolleyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}