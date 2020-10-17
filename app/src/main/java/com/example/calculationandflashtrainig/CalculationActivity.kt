package com.example.calculationandflashtrainig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_calculation.*
import kotlinx.android.synthetic.main.activity_calculation.buttonCalc0
import java.util.*
import kotlin.concurrent.schedule

class CalculationActivity : AppCompatActivity(), View.OnClickListener {

    val handler = Handler()
    var timeValue = 0
    lateinit var runnable: Runnable
    lateinit var timer: Timer

    var calcOprator = ""
    var calcDigit = 0
    var calcRemainingNumber = 10
    var calcCorrectNumber = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculation)

        val bundle = intent.extras
        calcOprator = bundle!!.getString(getString(R.string.intent_key_oprator)).toString()
        textViewOprator.text = calcOprator
        calcDigit = bundle.getInt(getString(R.string.intent_key_digit))
        textViewDigit.text = calcDigit.toString() + getString(R.string.strDigit)
        calcCorrectNumber = 0

        runnable = object: Runnable {
            override fun run() {
                timeValue++
                timeText(timeValue)?.let { // ?.let → nullでない場合更新
                    textViewCalcTime.text = it // timeTextメソッドの値
                }
                handler.postDelayed(this, 1000)
            }
        }

        buttonCalc0.isEnabled = false
        buttonCalc1.isEnabled = false
        buttonCalc2.isEnabled = false
        buttonCalc3.isEnabled = false
        buttonCalc4.isEnabled = false
        buttonCalc5.isEnabled = false
        buttonCalc6.isEnabled = false
        buttonCalc7.isEnabled = false
        buttonCalc8.isEnabled = false
        buttonCalc9.isEnabled = false
        buttonCalcMinus.isEnabled = false
        buttonCalcClear.isEnabled = false
        buttonCalcAnswerCheck.visibility = View.INVISIBLE

        buttonCalc0.setOnClickListener(this)
        buttonCalc1.setOnClickListener(this)
        buttonCalc2.setOnClickListener(this)
        buttonCalc3.setOnClickListener(this)
        buttonCalc4.setOnClickListener(this)
        buttonCalc5.setOnClickListener(this)
        buttonCalc6.setOnClickListener(this)
        buttonCalc7.setOnClickListener(this)
        buttonCalc8.setOnClickListener(this)
        buttonCalc9.setOnClickListener(this)
        buttonCalcMinus.setOnClickListener(this)
        buttonCalcClear.setOnClickListener(this)

        buttonCalcStart.setOnClickListener {
            calcStart()
        }

        buttonCalcAnswerCheck.setOnClickListener {
            if (textViewCalcAnswer.text.toString() != "" && textViewCalcAnswer.text.toString() != "-") {
                calcAnswerCheck()
            }
        }

        buttonCalcEnd.setOnClickListener {
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        timer = Timer()
    }


    override fun onPause() {
        super.onPause()
        timer.cancel()
    }


    private fun timeText(time: Int = 0): String? { // 引数を00:00:00形式に変換
        return if (time < 0) {
            null
        } else if (time == 0) {
            "00:00:00"
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)
        }
    }


    private fun calcStart() {
        buttonCalcEnd.isEnabled = false
        buttonCalcStart.visibility = View.INVISIBLE
        buttonCalcAnswerCheck.visibility = View.VISIBLE
        calcQuestion()
        handler.post(runnable) // タイマースタート
    }


    private fun calcQuestion() {
        buttonCalc0.isEnabled = true
        buttonCalc1.isEnabled = true
        buttonCalc2.isEnabled = true
        buttonCalc3.isEnabled = true
        buttonCalc4.isEnabled = true
        buttonCalc5.isEnabled = true
        buttonCalc6.isEnabled = true
        buttonCalc7.isEnabled = true
        buttonCalc8.isEnabled = true
        buttonCalc9.isEnabled = true
        buttonCalcMinus.isEnabled = true
        buttonCalcClear.isEnabled = true
        buttonCalcAnswerCheck.isEnabled = true
        textViewCalcAnswer.text = ""
        imageViewCalc.visibility = View.INVISIBLE

        val rnd = Random()
        when (calcDigit) {
            1 -> textViewCalcLeft.text = rnd.nextInt(10).toString()
            2 -> textViewCalcLeft.text = (rnd.nextInt(90) + 10).toString()
            3 -> textViewCalcLeft.text = (rnd.nextInt(900) + 100).toString()
            4 -> textViewCalcLeft.text = (rnd.nextInt(9000) + 1000).toString()
        }
        when (calcDigit) {
            1 -> textViewCalcRight.text = rnd.nextInt(10).toString()
            2 -> textViewCalcRight.text = (rnd.nextInt(90) + 10).toString()
            3 -> textViewCalcRight.text = (rnd.nextInt(900) + 100).toString()
            4 -> textViewCalcRight.text = (rnd.nextInt(9000) + 1000).toString()
        }

        when (calcOprator) {
            "+" -> textViewCalcOprator.text = calcOprator
            "-" -> textViewCalcOprator.text = calcOprator
            "*" -> textViewCalcOprator.text = calcOprator
            "+ - *" -> when (rnd.nextInt(3) + 1) {
                            1 -> textViewCalcOprator.text = "+"
                            2 -> textViewCalcOprator.text = "-"
                            3 -> textViewCalcOprator.text = "*"
                        }
        }
        calcRemainingNumber--
        textViewCalcRemaining.text = calcRemainingNumber.toString() + getString(R.string.strQuestion)
    }


    private fun calcAnswerCheck() {
        buttonCalc0.isEnabled = false
        buttonCalc1.isEnabled = false
        buttonCalc2.isEnabled = false
        buttonCalc3.isEnabled = false
        buttonCalc4.isEnabled = false
        buttonCalc5.isEnabled = false
        buttonCalc6.isEnabled = false
        buttonCalc7.isEnabled = false
        buttonCalc8.isEnabled = false
        buttonCalc9.isEnabled = false
        buttonCalcMinus.isEnabled = false
        buttonCalcClear.isEnabled = false
        buttonCalcAnswerCheck.isEnabled = false
        imageViewCalc.visibility = View.VISIBLE

        val calcAnswer: Int = textViewCalcAnswer.text.toString().toInt()
        var calcAnswerTruth: Int = 0
        if (textViewCalcOprator.text == "+") {
            calcAnswerTruth = textViewCalcLeft.text.toString().toInt() + textViewCalcRight.text.toString().toInt()
        } else if (textViewCalcOprator.text == "-") {
            calcAnswerTruth = textViewCalcLeft.text.toString().toInt() - textViewCalcRight.text.toString().toInt()
        } else {
            calcAnswerTruth = textViewCalcLeft.text.toString().toInt() * textViewCalcRight.text.toString().toInt()
        }
        if (calcAnswer == calcAnswerTruth) {
            calcCorrectNumber++
            textViewCalcCorrect.text = calcCorrectNumber.toString() + getString(R.string.strQuestion)
            imageViewCalc.setImageResource(R.drawable.correct)
        } else {
            imageViewCalc.setImageResource(R.drawable.incorrect)
        }

        if (calcRemainingNumber == 0) {
            handler.removeCallbacks(runnable) // タイマーストップ
            buttonCalcEnd.isEnabled = true
        } else {
            timer.schedule(1500, {runOnUiThread { calcQuestion()}})
        }
    }


    override fun onClick(p0: View?) {
        val button: Button = p0 as Button
        when (p0.id) {
            R.id.buttonCalcClear -> textViewCalcAnswer.text = ""
            R.id.buttonCalcMinus ->  if (textViewCalcAnswer.text.toString() == "") textViewCalcAnswer.text = "-"
            R.id.buttonCalc0 -> if (textViewCalcAnswer.text.toString() != "0" && textViewCalcAnswer.text.toString() != "-")
                                    textViewCalcAnswer.append(button.text)
            else -> if (textViewCalcAnswer.text.toString() == "0") textViewCalcAnswer.text = button.text
                    else textViewCalcAnswer.append(button.text)
        }
    }

}