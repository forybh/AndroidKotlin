package com.example.myapp04autotext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    var countries= mutableListOf<String>(
            "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
            "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
            "Armenia", "Aruba", "Austrailia", "Austria", "Azerbaijan",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium"
    )
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    fun init() {
        val autoText = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val countries2 = resources.getStringArray(R.array.countries_array)

        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        autoText.setAdapter(adapter)

        val multiAutoText = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        multiAutoText.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        multiAutoText.setAdapter(adapter)

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        editText.addTextChangedListener {
            var str = it.toString()
            button.isEnabled = str.isNotEmpty()
        }

//        editText.addTextChangedListener(
//                afterTextChanged = {
//                    val str = it.toString()
//                    button.isEnabled = str.isNotEmpty()
//                }
//        )

//        editText.addTextChangedListener(object:TextWatcher{
//            override fun afterTextChanged(p0: Editable?) {
//                val str = p0.toString()
//                button.isEnabled = str.isNotEmpty()
//                // TODO("Not yet implemented")
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                // TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                // TODO("Not yet implemented")
//            }
//
//        })
        button.setOnClickListener {
            adapter.add(editText.text.toString())
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }

    }
}