package com.example.calculationandflashtrainig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCalculationGame.setOnClickListener {
            val intent = Intent(this@MainActivity, CalculationActivity::class.java)
            when (radioGroupCalcOperator.checkedRadioButtonId) {
                R.id.radioButtonCalcPlus -> intent.putExtra(getString(R.string.intent_key_oprator), "+")
                R.id.radioButtonCalcMinus -> intent.putExtra(getString(R.string.intent_key_oprator), "-")
                R.id.radioButtonCalcMultiplied -> intent.putExtra(getString(R.string.intent_key_oprator), "*")
                R.id.radioButtonCalcAll -> intent.putExtra(getString(R.string.intent_key_oprator), "+ - *")
            }
            when (radioGroupCalcDigit.checkedRadioButtonId) {
                R.id.radioButtonCalcDigit1 -> intent.putExtra(getString(R.string.intent_key_digit), 1)
                R.id.radioButtonCalcDigit2 -> intent.putExtra(getString(R.string.intent_key_digit), 2)
                R.id.radioButtonCalcDigit3 -> intent.putExtra(getString(R.string.intent_key_digit), 3)
                R.id.radioButtonCalcDigit4 -> intent.putExtra(getString(R.string.intent_key_digit), 4)
            }
            startActivity(intent)
        }

        buttonFlashGame.setOnClickListener {
            val intent = Intent(this@MainActivity, FlashgameActivity::class.java)
            when (radioGroupFlashTime.checkedRadioButtonId) {
                R.id.radioButtonFlashTime1 -> intent.putExtra(getString(R.string.intent_key_time), 1)
                R.id.radioButtonFlashTime2 -> intent.putExtra(getString(R.string.intent_key_time), 2)
                R.id.radioButtonFlashTime3 -> intent.putExtra(getString(R.string.intent_key_time), 3)
            }
            when (radioGroupFlashDigit.checkedRadioButtonId) {
                R.id.radioButtonFlashDigit1 -> intent.putExtra(getString(R.string.intent_key_digit_flash), 5)
                R.id.radioButtonFlashDigit2 -> intent.putExtra(getString(R.string.intent_key_digit_flash), 8)
                R.id.radioButtonFlashDigit3 -> intent.putExtra(getString(R.string.intent_key_digit_flash), 10)
            }
            startActivity(intent)
        }
    }
}