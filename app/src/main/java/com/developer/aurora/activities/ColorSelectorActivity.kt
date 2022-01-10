package com.developer.aurora.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.developer.aurora.R
import com.developer.aurora.adapters.ColorSelectorRecyclerAdapter
import com.developer.aurora.databinding.ActivityColorScelectorBinding
import com.developer.aurora.models.Colors
import android.content.Intent




class ColorSelectorActivity : AppCompatActivity(), ColorSelectorRecyclerAdapter.OnItemClick {
    lateinit var binding:ActivityColorScelectorBinding
    val list:ArrayList<Colors> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityColorScelectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoadColors()
        binding.colorRecyclerView.adapter=ColorSelectorRecyclerAdapter(list,this)


    }

    private fun initLoadColors() {
        list.add(Colors("#B71C1C","#D32F2F","#FF5252"))
        list.add(Colors("#FB8C00","#FF9800","#F9A825"))
        list.add(Colors("#FBC02D","#FFCC33","#FFD740"))
        list.add(Colors("#5Eb100","#72C419","#86D833"))
        list.add(Colors("#4399B3","#58ACC6","#6CBFDA"))
        list.add(Colors("#2064CD","#4075E1","#5987F5"))
        list.add(Colors("#9750CF","#AB62E3","#BF74F7"))
        list.add(Colors("#C65094","#DB63A7","#F076BA"))
        list.add(Colors("#676767","#787878","#8A8A8A"))

    }

    override fun onColorClick(hex: String) {
        val intent = Intent()
        intent.putExtra("color", hex)
        setResult(Activity.RESULT_OK, intent)
        finish() //finishing activity

    }
}