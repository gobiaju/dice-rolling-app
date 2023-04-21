 package ca.gobiaju.dice_rolling_app

 import android.content.Context
 import android.os.Bundle
 import android.view.View
 import android.widget.*
 import androidx.appcompat.app.AppCompatActivity

 import kotlin.random.Random

 class MainActivity : AppCompatActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

         val diceImage = findViewById<ImageView>(R.id.imageView)
         val button = findViewById<Button>(R.id.button)
         val spinner = findViewById<Spinner>(R.id.diceSpinner)
         val textView = findViewById<TextView>(R.id.textView)
         val selectedItemView = findViewById<TextView>(R.id.textView2)
         val displaySavedPreference = findViewById<TextView>(R.id.textView3)
         val savedPreferences = findViewById<EditText>(R.id.savedPref)
         val saveButton = findViewById<Button>(R.id.saveButton)
         //save user values using sharedPreferences
         val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
         val savedVals = sharedPreferences.getString("myVals", "") // Retrieve saved values
         //display saved preferences
         displaySavedPreference.text = "saved values: $savedVals"
         //display default dice image
         diceImage.setImageResource(R.drawable.dice_1)

         // Mapping each dice type to the corresponding image prefix
         val diceTypes = mapOf(
             "dice 4" to "d4_dice_",
             "dice 6" to "dice_",
             "dice 8" to "d8_dice_",
             "dice 10" to "d10_dice_"
         )
         // set the spinner with the diceTypes
         val adapter =
             ArrayAdapter(this, android.R.layout.simple_spinner_item, diceTypes.keys.toList())
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
         spinner.adapter = adapter
         // set dice 6 as default dice
         spinner.setSelection(adapter.getPosition("dice 6"))
        //display the selected dice
         spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 if (spinner.selectedItem != null){
                 selectedItemView.text = "Selected dice: ${parent?.getItemAtPosition(position)}"
                 } else {
                         selectedItemView.text = "No dice selected"
                 }
             }
             override fun onNothingSelected(parent: AdapterView<*>?) {
                 selectedItemView.text = "No dice selected"
             }

         }
         saveButton.setOnClickListener {
             val myValues = savedPreferences.text.toString()
             val editor = sharedPreferences.edit()
             editor.putString("myVals", myValues)
             editor.apply()
             Toast.makeText(this, "Values saved!", Toast.LENGTH_SHORT).show()
         }
         button.setOnClickListener {
             val selectedDiceType = spinner.selectedItem.toString()

             // Get the prefix for the selected dice type
             val dicePrefix = diceTypes.getValue(selectedDiceType)

             // Set the number of sides based on the selected dice type
             val sides = diceTypes[selectedDiceType]?.removePrefix("dice")?.toIntOrNull() ?: 6

             // Generate a random number between 1 and the number of sides
             val result = (1..sides).random()

             // Get the drawable ID for the corresponding dice image
             val drawableId = resources.getIdentifier(
                 "${dicePrefix}${result}",
                 "drawable",
                 "ca.gobiaju.dice_rolling_app"
             )

             // Set the dice image and result text
             diceImage.setImageResource(drawableId)
             textView.text = result.toString()
         }
     }
 }