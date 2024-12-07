package me.arycer.dam

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        configureEdgeToEdge()

        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.autoComplete_id)
        val editText: EditText = findViewById(R.id.edit_id)
        val spinner: Spinner = findViewById(R.id.spinner_id)
        val toggleButton: ToggleButton = findViewById(R.id.toggleButton)
        val switch: Switch = findViewById(R.id.switch_id)

        val languages = listOf("Español", "Inglés", "Francés", "Portugués", "Japonés", "Alemán")
        setupAutoCompleteTextView(autoCompleteTextView, languages)
        setupSpinner(spinner, languages)

        setupEditTextListener(editText)
        setupAutoCompleteListener(autoCompleteTextView)
        setupToggleButtonListener(toggleButton)
        setupSwitchListener(switch)
        setupSpinnerListener(spinner)
    }

    private fun configureEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupAutoCompleteTextView(autoCompleteTextView: AutoCompleteTextView, languages: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languages)
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun setupSpinner(spinner: Spinner, languages: List<String>) {
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner
    }

    private fun setupEditTextListener(editText: EditText) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val inputText = editText.text.toString()
                showToast("Has escrito: $inputText")
            }
        }
    }

    private fun setupAutoCompleteListener(autoCompleteTextView: AutoCompleteTextView) {
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = autoCompleteTextView.adapter.getItem(position).toString()
            showToast("Idioma seleccionado: $selectedItem")
        }
    }

    private fun setupToggleButtonListener(toggleButton: ToggleButton) {
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Botón activado" else "Botón desactivado"
            showToast(message)
        }
    }

    private fun setupSwitchListener(@SuppressLint("UseSwitchCompatOrMaterialCode") switch: Switch) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Switch activado" else "Switch desactivado"
            showToast(message)
        }
    }

    private fun setupSpinnerListener(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                showToast("Idioma seleccionado: $selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ignorar
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}