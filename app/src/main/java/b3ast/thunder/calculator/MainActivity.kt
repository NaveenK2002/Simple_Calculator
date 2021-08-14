package b3ast.thunder.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false

    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    fun onDigit(view: View) {
        checkIfLimitReached ()
        tvInput.append((view as Button).text)

        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        checkIfLimitReached ()
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        checkIfLimitReached ()
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onBackSpace(view: View) {
        var tempString = tvInput.text.toString()
        if (tempString.isNotEmpty())
        {
            var deletedCharacter: Char = tempString.last()
            if (deletedCharacter.isDigit()){
                tempString = tempString.substring(0, tempString.length-1)
            }
            else if (deletedCharacter=='+'||deletedCharacter=='-'||deletedCharacter=='*'||deletedCharacter=='/'){
                tempString = tempString.substring(0, tempString.length-1)
                lastNumeric= true
                lastDot = false
            }
            else if (deletedCharacter=='.'){
                tempString = tempString.substring(0, tempString.length-1)
                lastDot= false
                lastNumeric = true
            }
        }
        if(tempString.isEmpty()){
            tempString = ""
            lastNumeric = false
            lastDot = false
        }
        tvInput.text = tempString
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = tvInput.text.toString()
            var prefix = ""
            try {
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1);
                }
                if (value.contains("/")) {
                    val splitedValue = value.split("/")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (value.contains("*")) {
                    val splitedValue = value.split("*")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (value.contains("-")) {

                    val splitedValue = value.split("-")

                    var one = splitedValue[0]
                    val two = splitedValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (value.contains("+")) {

                    val splitedValue = value.split("+")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
            if(tvInput.text.contains(".")){
                lastDot = true
            }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {

        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun checkIfLimitReached (){
        if(tvInput.text.toString().length==12){
            Toast.makeText(this
                , "Input limit of 12 characters reached! Clear or Remove!"
                ,Toast.LENGTH_LONG).show()
        }
    }
}