package com.example.plantdetection

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.SearchView

class PlantSolution : AppCompatActivity() {

    private val diseases = listOf(
        Disease("Apple scab", "Treatment: Apply fungicides like captan or myclobutanil in early spring. Prune infected branches and clear fallen leaves to reduce fungal spores."),
        Disease("Black rot", "Treatment: Remove infected fruit and branches. Apply copper-based fungicides during early growth stages."),
        Disease("Cedar apple rust", "Treatment: Use fungicides like myclobutanil. Remove nearby cedar trees if possible, as they host the fungus."),
        Disease("Healthy", "No treatment needed."),
        Disease("Not a plant", "No solution needed."),
        Disease("Powdery mildew", "Treatment: Apply sulfur or neem oil sprays. Ensure good air circulation around plants."),
        Disease("Cercospora leaf spot / Gray leaf spot", "Treatment: Apply chlorothalonil or mancozeb fungicides. Remove and dispose of affected leaves."),
        Disease("Common rust", "Treatment: Use fungicides like myclobutanil. Remove and destroy infected leaves."),
        Disease("Northern Leaf Blight", "Treatment: Use fungicides such as mancozeb. Rotate crops and plant resistant varieties."),
        Disease("Healthy", "No treatment needed."),
        Disease("Esca (Black Measles)", "Treatment: Remove infected vines and avoid over-irrigation. No specific cure; prevention is key."),
        Disease("Leaf blight (Isariopsis Leaf Spot)", "Treatment: Use copper fungicides. Avoid overhead watering to reduce moisture on leaves."),
        Disease("Huanglongbing (Citrus greening)", "Treatment: Remove infected trees to prevent spread. Use insecticides to control Asian citrus psyllid, which spreads the disease."),
        Disease("Bacterial spot", "Treatment: Apply copper-based bactericides. Remove infected plant parts."),
        Disease("Pepper, bell - Bacterial spot", "Treatment: Use copper sprays and avoid overhead watering. Remove infected plants."),
        Disease("Pepper, bell - Healthy", "No treatment needed."),
        Disease("Potato - Early blight", "Treatment: Apply fungicides like chlorothalonil. Rotate crops and remove infected leaves."),
        Disease("Potato - Late blight", "Treatment: Use fungicides such as mancozeb or chlorothalonil. Remove and destroy infected plants."),
        Disease("Potato - Healthy", "No treatment needed."),
        Disease("Raspberry - Healthy", "No treatment needed."),
        Disease("Soybean - Healthy", "No treatment needed."),
        Disease("Squash - Powdery mildew", "Treatment: Apply potassium bicarbonate or neem oil sprays. Ensure proper spacing for air circulation."),
        Disease("Strawberry - Leaf scorch", "Treatment: Remove infected leaves and avoid overhead watering. Apply fungicides if necessary."),
        Disease("Strawberry - Healthy", "No treatment needed."),
        Disease("Early blight", "Treatment: Use fungicides like chlorothalonil. Remove infected plant parts."),
        Disease("Late blight", "Treatment: Apply fungicides such as mancozeb or chlorothalonil. Remove infected plants."),
        Disease("Leaf Mold", "Treatment: Apply fungicides and ensure proper ventilation in growing areas."),
        Disease("Septoria leaf spot", "Treatment: Use copper fungicides. Remove infected leaves and avoid overhead watering."),
        Disease("Spider mites / Two-spotted spider mite", "Treatment: Use insecticidal soap or neem oil. Increase humidity around plants as mites thrive in dry conditions."),
        Disease("Target Spot", "Treatment: Use fungicides such as chlorothalonil. Remove affected leaves and ensure good air circulation."),
        Disease("Yellow Leaf Curl Virus", "Treatment: Control whitefly populations, which spread the virus, with insecticides. Remove infected plants."),
        Disease("Mosaic virus", "Treatment: No cure; remove infected plants to prevent spread. Control aphids, which spread the virus."),
        Disease("Healthy", "No treatment needed.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_solution)

        val adapter = DiseaseAdapter(this, diseases)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedDisease = adapter.getItem(position)
            selectedDisease?.let { showSolutionDialog(it) }
        }
    }

    private fun showSolutionDialog(disease: Disease) {
        AlertDialog.Builder(this)
            .setTitle(disease.name)
            .setMessage(disease.solution)
            .setPositiveButton("OK", null)
            .show()
    }
}
