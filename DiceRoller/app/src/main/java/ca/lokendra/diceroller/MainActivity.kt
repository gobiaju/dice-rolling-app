package ca.lokendra.diceroller

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var spinner: Spinner
    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        button = findViewById(R.id.button)
        spinner = findViewById(R.id.diceSpinner)
        textView = findViewById(R.id.textView)
        imageView.setImageResource(R.drawable.dice_1)


        // Map each dice type to its corresponding dice image prefix
        val diceTypes = mapOf(
            "d4" to "d4_dice_",
            "d6" to "dice_",
            "d8" to "d8_dice_",
            "d10" to "d10_dice_"
        )
        // Set up the spinner with the dice types
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, diceTypes.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set the default selected item in the spinner to "d6"
        spinner.setSelection(adapter.getPosition("d6"))


        button.setOnClickListener {
            val selectedDiceType = spinner.selectedItem.toString()

            // Get the prefix for the selected dice type
            val dicePrefix = diceTypes[selectedDiceType]

            // Set the number of sides based on the selected dice type
            val sides = when (selectedDiceType) {
                "d4" -> 4
                "d6" -> 6
                "d8" -> 8
                "d10" -> 10
                else -> 6
            }

            // Generate a random number between 1 and the number of sides
            val result = Random.nextInt(1, sides + 1)

            // Get the drawable ID for the corresponding dice image
            val drawableId = resources.getIdentifier(
                "${dicePrefix}${result}",
                "drawable",
                "ca.lokendra.diceroller"
            )

            // Set the dice image and result text
            imageView.setImageResource(drawableId)
            textView.text = result.toString()
        }
    }
}
