package com.example.trafficlight.mvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trafficlight.R
import com.example.trafficlight.adapters.LightsRvAdapter
import com.example.trafficlight.base.ItemClickListener
import com.example.trafficlight.base.ItemType

class MainActivity : AppCompatActivity(R.layout.activity_main), MainView, ItemClickListener {

    private val presenter: MainPresenter = MainPresenterImpl(this)

    private lateinit var rvLights: RecyclerView
    private lateinit var lightsAdapter: LightsRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreateView()
    }

    override fun onDestroy() {
        presenter.onDestroyView()
        super.onDestroy()
    }

    override fun init() {
        rvLights = findViewById(R.id.rv_lights)
        lightsAdapter = LightsRvAdapter(this)
        rvLights.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = lightsAdapter
        }
    }

    override fun setLightsData(list: List<ItemType>) = lightsAdapter.itemRange(list)

    override fun onItemClicked(item: ItemType) = presenter.onItemClicked(item)

    override fun showItemInfo(info: String) = Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
}