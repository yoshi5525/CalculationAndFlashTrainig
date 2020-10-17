package com.example.calculationandflashtrainig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_flashgame.*
import java.util.*
import kotlin.concurrent.schedule

class FlashgameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var timer: Timer

    var flashTime = 0
    var flashDigit = 0
    var flashQuestionNumber: Long = 0
    var flashRemainingNumber = 10
    var flashCorrectNumber = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashgame)

        val bundle = intent.extras
        flashTime = bundle!!.getInt(getString(R.string.intent_key_time)).toString().toInt()
        textViewFlashTime.text = flashTime.toString() + getString(R.string.strSeconds)
        flashDigit = bundle.getInt(getString(R.string.intent_key_digit_flash)).toString().toInt()
        textViewFlashDigit.text = flashDigit.toString() + getString(R.string.strDigit)
        textViewInfo.text = flashDigit.toString() + getString(R.string.strMessage)

        buttonFlash0.isEnabled = false
        buttonFlash1.isEnabled = false
        buttonFlash2.isEnabled = false
        buttonFlash3.isEnabled = false
        buttonFlash4.isEnabled = false
        buttonFlash5.isEnabled = false
        buttonFlash6.isEnabled = false
        buttonFlash7.isEnabled = false
        buttonFlash8.isEnabled = false
        buttonFlash9.isEnabled = false
        buttonFlashClear.isEnabled = false
        buttonFlashBack.isEnabled = false
        buttonFlashAnswerCheck.visibility = View.INVISIBLE
        textViewFlashAnswer.visibility = View.INVISIBLE
        textViewFlashRealAnswer.visibility = View.INVISIBLE

        buttonFlash0.setOnClickListener(this)
        buttonFlash1.setOnClickListener(this)
        buttonFlash2.setOnClickListener(this)
        buttonFlash3.setOnClickListener(this)
        buttonFlash4.setOnClickListener(this)
        buttonFlash5.setOnClickListener(this)
        buttonFlash6.setOnClickListener(this)
        buttonFlash7.setOnClickListener(this)
        buttonFlash8.setOnClickListener(this)
        buttonFlash9.setOnClickListener(this)
        buttonFlashClear.setOnClickListener(this)
        buttonFlashBack.setOnClickListener(this)

        buttonFlashStart.setOnClickListener {
            flashStart()
        }

        buttonFlashAnswerCheck.setOnClickListener {
            if ((textViewFlashAnswer.text.toString()).length == flashDigit) {
                flashAnswerCheck()
            }
        }

        buttonFlashEnd.setOnClickListener {
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


    private fun flashStart() {
        buttonFlashEnd.isEnabled = false
        buttonFlashStart.visibility = View.INVISIBLE
        buttonFlashAnswerCheck.visibility = View.VISIBLE
        flashQuestion()
    }


    private fun flashQuestion() {
        textViewFlashAnswer.text = ""
        textViewFlashAnswer.visibility = View.INVISIBLE
        textViewFlashRealAnswer.visibility = View.INVISIBLE
        imageViewFlash.visibility = View.INVISIBLE

        val rnd = Random()
        when (flashDigit) {
            5 -> flashQuestionNumber = (rnd.nextInt(90000)  + 10000).toLong()
            8 -> flashQuestionNumber = (rnd.nextInt(90000000) + 10000000).toLong()
            10 -> flashQuestionNumber = (rnd.nextInt(9000000000.toInt()) + 1000000000).toLong()
        }

        val longFlashTime: Long = (flashTime * 1000).toLong()
        textViewFlashQuestion.visibility = View.VISIBLE
        textViewFlashQuestion.text = flashQuestionNumber.toString()
        timer.schedule(longFlashTime, {runOnUiThread { flashQuestionAnswer()}})
        flashRemainingNumber--
        textViewFlashRemaining.text = flashRemainingNumber.toString() + getString(R.string.strQuestion)
    }


    private fun flashQuestionAnswer() {
        textViewFlashQuestion.visibility = View.INVISIBLE
        textViewFlashAnswer.visibility = View.VISIBLE
        buttonFlash0.isEnabled = true
        buttonFlash1.isEnabled = true
        buttonFlash2.isEnabled = true
        buttonFlash3.isEnabled = true
        buttonFlash4.isEnabled = true
        buttonFlash5.isEnabled = true
        buttonFlash6.isEnabled = true
        buttonFlash7.isEnabled = true
        buttonFlash8.isEnabled = true
        buttonFlash9.isEnabled = true
        buttonFlashClear.isEnabled = true
        buttonFlashBack.isEnabled = true
        buttonFlashAnswerCheck.isEnabled = true
    }


    private fun flashAnswerCheck() {
        buttonFlash0.isEnabled = false
        buttonFlash1.isEnabled = false
        buttonFlash2.isEnabled = false
        buttonFlash3.isEnabled = false
        buttonFlash4.isEnabled = false
        buttonFlash5.isEnabled = false
        buttonFlash6.isEnabled = false
        buttonFlash7.isEnabled = false
        buttonFlash8.isEnabled = false
        buttonFlash9.isEnabled = false
        buttonFlashClear.isEnabled = false
        buttonFlashBack.isEnabled = false
        buttonFlashAnswerCheck.isEnabled = false
        imageViewFlash.visibility = View.VISIBLE
        textViewFlashRealAnswer.visibility = View.VISIBLE

        val flashAnswer: Long = textViewFlashAnswer.text.toString().toLong()
        if (flashQuestionNumber == flashAnswer) {
            flashCorrectNumber++
            textViewFlashCorrect.text = flashCorrectNumber.toString() + getString(R.string.strQuestion)
            imageViewFlash.setImageResource(R.drawable.correct)
        } else {
            imageViewFlash.setImageResource(R.drawable.incorrect)
        }
        textViewFlashRealAnswer.text = flashQuestionNumber.toString()

        if (flashRemainingNumber == 0) {
            buttonFlashEnd.isEnabled = true
        } else {
            timer.schedule(1500, {runOnUiThread { flashQuestion()}})
        }
    }


    override fun onClick(p0: View?) {
        val button: Button = p0 as Button
        when (p0.id) {
            R.id.buttonFlashClear -> textViewFlashAnswer.text = ""
            R.id.buttonFlashBack -> if (textViewFlashAnswer.length() >= 1)
                textViewFlashAnswer.text = textViewFlashAnswer.text.substring(0, textViewFlashAnswer.text.length - 1)
            else -> textViewFlashAnswer.append(button.text)
        }
    }

}
