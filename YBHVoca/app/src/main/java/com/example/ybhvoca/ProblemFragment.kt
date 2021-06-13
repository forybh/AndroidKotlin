package com.example.ybhvoca

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.ybhvoca.databinding.FragmentProblemBinding
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.PrintStream


class ProblemFragment : Fragment() {
    private lateinit var viewModel: ProblemViewModel
    var binding:FragmentProblemBinding?= null
    val myViewModel:ProblemViewModel by activityViewModels()
    var selectList =  ArrayList<MyData>()
    var answerNum = 0
    var selectNum = 0
    var wrong = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProblemBinding.inflate(layoutInflater, container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            problem.setText(selectList[answerNum].word.toString())
            radioButton1.setText(selectList[0].meaning.toString())
            radioButton2.setText(selectList[1].meaning.toString())
            radioButton3.setText(selectList[2].meaning.toString())
            radioButton4.setText(selectList[3].meaning.toString())
            radioButton5.setText(selectList[4].meaning.toString())
            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when(i){
                    R.id.radioButton1 ->{
                        selectNum = 0
                    }
                    R.id.radioButton2 ->{
                        selectNum = 1
                    }
                    R.id.radioButton3 ->{
                        selectNum = 2
                    }
                    R.id.radioButton4 ->{
                        selectNum = 3
                    }
                    R.id.radioButton5 ->{
                        selectNum = 4
                    }
                }
                if(selectNum == answerNum) {
                    imageView.setImageResource(R.drawable.ic_baseline_trip_origin_24)
                    imageView.visibility = View.VISIBLE
                    Handler().postDelayed(Runnable {
                        val i = Intent(activity, TestActivity::class.java)
                        if(wrong != 0) {
                            myViewModel.setLiveData(selectList[answerNum])
                            i.putExtra("word", selectList[answerNum])
                            i.putExtra("mean", selectList[answerNum])
                        }
                        startActivity(i)
                    }, 3000)
                }
                else {
                    FirebaseDatabase.getInstance().getReference("MyData/items")
                            .child("2").
                            child(selectList[answerNum].word).
                            setValue(MyData(2,selectList[answerNum].word,selectList[answerNum].meaning,0))
                    imageView.setImageResource(R.drawable.ic_baseline_clear_24)
                    imageView.visibility = View.VISIBLE
                    wrong += 1

                }
                Log.d("정답은", i.toString() + " " + answerNum.toString() + " " + selectList[answerNum].toString())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProblemViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            selectList.clear()
            val mean1 = arguments?.getString("mean1")
            val word1 = arguments?.getString("word1")
            val mean2 = arguments?.getString("mean2")
            val word2 = arguments?.getString("word2")
            val mean3 = arguments?.getString("mean3")
            val word3 = arguments?.getString("word3")
            val mean4 = arguments?.getString("mean4")
            val word4 = arguments?.getString("word4")
            val mean5 = arguments?.getString("mean5")
            val word5 = arguments?.getString("word5")
            answerNum = arguments?.getInt("answerNum")!!
            selectList.add(MyData(0,word1!!,mean1!!,0))
            selectList.add(MyData(0,word2!!,mean2!!,0))
            selectList.add(MyData(0,word3!!,mean3!!,0))
            selectList.add(MyData(0,word4!!,mean4!!,0))
            selectList.add(MyData(0,word5!!,mean5!!,0))
        }
    }

}