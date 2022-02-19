package com.work.part2.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val clearbutton: Button by lazy {
        findViewById(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById(R.id.addButton)
    }
    private val runButton: Button by lazy {
        findViewById(R.id.runButton)
    }


    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.numberPicker)
    }

    private val numberTextView: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.firstNumber),
            findViewById<TextView>(R.id.secondNumber),
            findViewById<TextView>(R.id.thirdNumber),
            findViewById<TextView>(R.id.fourthNumber),
            findViewById<TextView>(R.id.fifthNumber),
            findViewById<TextView>(R.id.sixthNumber)
        )
    }

    private var didRun = false

    //1~45 중복된 숫자를 추가하지 않도록
    private val pickNumberSet = hashSetOf<Int>()//mutable을 사용해도 된다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //numberPicker 범위 정하기
        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()

            Log.d("Main", list.toString())
        }
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //선택한 번호를 또 선택했을 때
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택된 번호입니다.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //영상에서는 apply를 하지 않고 textVIew. 으로 설정하였다.
            val textView = numberTextView[pickNumberSet.size]
                .apply{
                    this.isVisible = true
                    this.text = numberPicker.value.toString()
                    pickNumberSet.add(numberPicker.value)
                }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    this.add(i)
                }
            }
        numberList.shuffle()

        return numberList.subList(0, 6).sorted()
    }
}