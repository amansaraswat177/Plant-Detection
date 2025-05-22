package com.example.plantdetection

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.SearchView

class PlantSolution : AppCompatActivity() {

    private val diseases = listOf(
        Disease(
            "Apple scab",
            "Apply fungicides like captan or myclobutanil during early spring. Prune infected branches and clear fallen leaves.",
            "Olive-green to brown spots on leaves, fruit, and twigs.",
            "Ensure proper air circulation and regular pruning. Use resistant apple varieties.",
            "Wet and humid conditions during spring."
        ),
        Disease(
            "Black rot",
            "Remove infected fruit and branches. Apply copper-based fungicides during early growth stages.",
            "Circular black lesions on fruit and leaves.",
            "Prune trees regularly and remove debris around trees. Use resistant varieties.",
            "Warm, humid weather conditions."
        ),
        Disease(
            "Cedar apple rust",
            "Use fungicides like myclobutanil. Remove nearby cedar trees to eliminate the alternate host.",
            "Yellow-orange spots on leaves and fruit.",
            "Plant resistant apple varieties. Avoid planting apple trees near cedar trees.",
            "Proximity to cedar trees and humid conditions."
        ),
        Disease(
            "Powdery mildew",
            "Apply sulfur or neem oil sprays. Ensure good air circulation around plants.",
            "White, powdery fungal growth on leaves and stems.",
            "Avoid overcrowding plants. Water early in the day to allow foliage to dry.",
            "Dry, warm weather and poor air circulation."
        ),
        Disease(
            "Cercospora leaf spot / Gray leaf spot",
            "Apply chlorothalonil or mancozeb fungicides. Remove and dispose of affected leaves.",
            "Small, grayish-brown spots on leaves, sometimes with a yellow halo.",
            "Rotate crops and use resistant plant varieties.",
            "High humidity and warm temperatures."
        ),
        Disease(
            "Common rust",
            "Use fungicides like myclobutanil. Remove and destroy infected leaves.",
            "Small, reddish-brown pustules on leaves and stalks.",
            "Plant resistant varieties. Avoid overhead irrigation.",
            "Cool nights followed by warm, humid days."
        ),
        Disease(
            "Northern Leaf Blight",
            "Use fungicides such as mancozeb. Rotate crops and plant resistant varieties.",
            "Cigar-shaped, gray-brown lesions on leaves.",
            "Plant resistant hybrids. Remove plant debris post-harvest.",
            "Prolonged leaf wetness and humid conditions."
        ),
        Disease(
            "Esca (Black Measles)",
            "Remove infected vines and avoid over-irrigation. No specific cure; prevention is key.",
            "Dark streaks on wood, leaves with interveinal chlorosis, and fruit discoloration.",
            "Maintain proper irrigation practices. Avoid injuries to vines.",
            "Older grapevines and environmental stress."
        ),
        Disease(
            "Leaf blight (Isariopsis Leaf Spot)",
            "Use copper fungicides. Avoid overhead watering to reduce moisture on leaves.",
            "Brown spots on leaves with yellow margins.",
            "Use resistant varieties and proper crop rotation.",
            "High humidity and poor field sanitation."
        ),
        Disease(
            "Huanglongbing (Citrus greening)",
            "Remove infected trees to prevent spread. Use insecticides to control Asian citrus psyllid.",
            "Yellowing of leaves, misshapen fruit, and bitter taste.",
            "Control psyllid populations and use certified disease-free plants.",
            "Presence of psyllids and infected trees nearby."
        ),
        Disease(
            "Bacterial spot",
            "Apply copper-based bactericides. Remove infected plant parts.",
            "Water-soaked spots on leaves and fruit, which turn dark and scab-like.",
            "Avoid overhead watering. Use disease-free seeds.",
            "Wet weather and contaminated seeds."
        ),
        Disease(
            "Early blight",
            "Use fungicides like chlorothalonil. Remove infected plant parts.",
            "Dark, concentric spots on leaves, often with a yellow halo.",
            "Crop rotation and resistant varieties.",
            "Warm temperatures and high humidity."
        ),
        Disease(
            "Late blight",
            "Apply fungicides such as mancozeb or chlorothalonil. Remove infected plants.",
            "Water-soaked, grayish lesions on leaves and stems.",
            "Plant resistant varieties and ensure good drainage.",
            "Cool, wet weather conditions."
        ),
        Disease(
            "Leaf Mold",
            "Apply fungicides and ensure proper ventilation in growing areas.",
            "Yellow spots on upper leaf surfaces with moldy growth underneath.",
            "Maintain good ventilation and avoid overcrowding.",
            "High humidity and poor airflow."
        ),
        Disease(
            "Septoria leaf spot",
            "Use copper fungicides. Remove infected leaves and avoid overhead watering.",
            "Small, circular spots with gray centers and dark borders.",
            "Clean up fallen debris and use disease-free seeds.",
            "Wet, humid conditions."
        ),
        Disease(
            "Spider mites / Two-spotted spider mite",
            "Use insecticidal soap or neem oil. Increase humidity around plants as mites thrive in dry conditions.",
            "Yellowing and stippling on leaves with webbing underneath.",
            "Increase humidity and avoid dry, dusty conditions.",
            "Hot, dry weather and stressed plants."
        ),
        Disease(
            "Yellow Leaf Curl Virus",
            "Control whitefly populations with insecticides. Remove infected plants.",
            "Yellowing and curling of leaves with stunted growth.",
            "Plant resistant varieties and control whiteflies.",
            "Whitefly infestations and warm climates."
        ),
        Disease(
            "Mosaic virus",
            "No cure; remove infected plants to prevent spread. Control aphids.",
            "Mottled, discolored patches on leaves.",
            "Control aphids and use disease-free seeds.",
            "Aphid infestations and infected seeds."
        )
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
        // Combine solution and treatment into a single string
        val message = "Solution: ${disease.solution}\n\nSymptoms: ${disease.symptoms}\n\nPreventive Measures: ${disease.preventiveMeasures}\n\nRisk Factor: ${disease.riskFactors}"

        AlertDialog.Builder(this)
            .setTitle(disease.name)
            .setMessage(message)  // Pass the combined message
            .setPositiveButton("OK", null)
            .show()
    }

}
