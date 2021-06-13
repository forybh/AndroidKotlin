package com.example.ybhvoca

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.ybhvoca.databinding.ActivityTestBinding
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList
import java.util.Random
import javax.security.auth.Subject

class TestActivity : AppCompatActivity() {
    var data = ArrayList<MyData>()
    lateinit var binding: ActivityTestBinding
    lateinit var problemFragment: ProblemFragment
    lateinit var subjectFragment: SubjectFragment
    val viewModel: ProblemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readFileScan()
        init()
        writeFile()
    }

    private fun init() {

        binding.apply {
            objectBtn.setOnClickListener {
                objectBtn.background = ContextCompat.getDrawable(it.context, R.drawable.clicked)
                subjectBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                val random = Random()
                var answerNum = random.nextInt(5)
                var selectList = ArrayList<MyData>()

                for(i:Int in 0..5){
                    var randomNum = random.nextInt(data.size)
                    selectList.add(data[randomNum])
                }
                var args = Bundle()
                args.putString("word1", selectList[0].word)
                args.putString("mean1", selectList[0].meaning)
                args.putString("word2", selectList[1].word)
                args.putString("mean2", selectList[1].meaning)
                args.putString("word3", selectList[2].word)
                args.putString("mean3", selectList[2].meaning)
                args.putString("word4", selectList[3].word)
                args.putString("mean4", selectList[3].meaning)
                args.putString("word5", selectList[4].word)
                args.putString("mean5", selectList[4].meaning)
                args.putInt("answerNum", answerNum)
                problemFragment = ProblemFragment()
                problemFragment.arguments = args
                var fragment = supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                fragment.replace(R.id.testframelayout, problemFragment, "problem")
                fragment.commit()
            }
            subjectBtn.setOnClickListener {
                subjectBtn.background = ContextCompat.getDrawable(it.context, R.drawable.clicked)
                objectBtn.background = ContextCompat.getDrawable(it.context, R.drawable.mybuttonstyle)
                val random = Random()
                var answerNum = random.nextInt(data.size)
                val answer = data[answerNum]
                var args = Bundle()
                args.putString("answer", answer.word)
                args.putString("answerMean", answer.meaning)

                subjectFragment = SubjectFragment()
                subjectFragment.arguments = args
                var fragment = supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                fragment.replace(R.id.testframelayout, subjectFragment, "subject")
                fragment.commit()

            }
        }


    }

    fun readFileScan(){
        var scan = Scanner(resources.openRawResource(R.raw.words))
        data = ArrayList()
        while (scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(0 ,word, meaning,0))
        }
        scan.close()
    }
    private fun writeFile() {
        val intent = Intent()
        val word = intent.getStringExtra("word")
        val mean = intent.getStringExtra("mean")
        if(word!=null){
            val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
            output.println(word)
            output.println(mean)
            Log.e("output",output.toString())
            output.close()
        }
    }
}