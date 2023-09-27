package com.pdm.alcoolougasolina

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    private var percentageValue: Double = 0.7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        percentageValue = savedInstanceState?.getDouble("percentageValue") ?: 0.7

        val btCalc: Button = findViewById(R.id.btCalculate)
        val edAlc: EditText = findViewById(R.id.edAlc)
        val edGas: EditText = findViewById(R.id.edGas)
        val swPercentage: SwitchCompat = findViewById(R.id.swPercentage)
        val resultField: TextView = findViewById(R.id.resultField)

        swPercentage.setOnCheckedChangeListener  { _, isChecked ->
            updatePercentageValue(isChecked)
        }

        btCalc.setOnClickListener {
            val alcoholValue: Double? = edAlc.text.toString().toDoubleOrNull()
            val gasolineValue: Double? = edGas.text.toString().toDoubleOrNull()

            if (alcoholValue != null && gasolineValue != null) {
                displayResult(alcoholValue, gasolineValue, percentageValue, resultField)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putDouble("percentageValue", percentageValue)
        super.onSaveInstanceState(outState)
    }

    private fun updatePercentageValue(isChecked: Boolean) {
        percentageValue = if (isChecked) 0.75 else 0.7
        Log.i("PDM23", "Percentage updated to $percentageValue")
    }

    private fun displayResult(
        alcoholValue: Double,
        gasolineValue: Double,
        percentage: Double,
        resultField: TextView,
    ) {
        Log.i("PDM23", "Alcohol: $alcoholValue, Gas: $gasolineValue, Percentage: $percentage")
        val isAlcoholCheaper = alcoholValue <= gasolineValue*percentage
        if (isAlcoholCheaper) {
            resultField.text = applicationContext.getString(R.string.alcohol)
            Log.i("PDM23", "Alcohol is cheaper")
        } else {
            resultField.text = applicationContext.getString(R.string.gas)
            Log.i("PDM23", "Gas is cheaper")
        }
    }
}