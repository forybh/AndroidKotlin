package com.example.ybhvoca

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ybhvoca.databinding.FragmentProblemBinding
import com.example.ybhvoca.databinding.FragmentSubjectBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class SubjectFragment : Fragment() {
    var binding: FragmentSubjectBinding?= null
    val myViewModel:ProblemViewModel by activityViewModels()
    lateinit var tts: TextToSpeech
    var answer = ""
    var answerMean = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectBinding.inflate(layoutInflater, container,false)
        initTTS()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            problem2.setText(answerMean)
            inputBtn.setOnClickListener {
                if(editTextAnswer.text.toString() == answer){
                    imageView2.setImageResource(R.drawable.ic_baseline_trip_origin_24)
                    imageView2.visibility = View.VISIBLE
                    Handler().postDelayed(Runnable {
                        val i = Intent(activity, TestActivity::class.java)
                        startActivity(i)
                    }, 3000)
                }
                else {
                    FirebaseDatabase.getInstance().getReference("MyData/items").child("2").child(answer).setValue(MyData(2,answer,answerMean,0))
                    imageView2.setImageResource(R.drawable.ic_baseline_clear_24)
                    imageView2.visibility = View.VISIBLE
                    tts.speak(answer, TextToSpeech.QUEUE_ADD, null, null)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        tts.shutdown()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            answer = arguments?.getString("answer").toString()
            answerMean = arguments?.getString("answerMean").toString()
        }
    }
    private fun initTTS() {
        tts = TextToSpeech(activity, TextToSpeech.OnInitListener {
            tts.language = Locale.US
        })
    }

}