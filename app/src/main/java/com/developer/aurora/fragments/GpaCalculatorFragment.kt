package com.developer.aurora.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.developer.aurora.adapters.GpaRecyclerAdapter
import com.developer.aurora.databinding.FragmentGpaCalculatorBinding
import com.developer.aurora.models.GpaModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GpaCalculatorFragment : Fragment(), GpaRecyclerAdapter.OnItemClick {
    private var _binding: FragmentGpaCalculatorBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GpaRecyclerAdapter
    private lateinit var subjectList: ArrayList<GpaModel>
    private val args: GpaCalculatorFragmentArgs by navArgs()

    private var map: HashMap<Int, String> = HashMap()
    private var mapArray: ArrayList<String> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGpaCalculatorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subjectList = ArrayList()
        adapter = GpaRecyclerAdapter(subjectList, this)
        binding.recyclerView.adapter = adapter
        creditSystem(args.branchName, args.semister)
        binding.calculateGpaBtn.setOnClickListener {
            if (map.isEmpty()) {
                Toast.makeText(requireContext(), "All Fields Are Required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                gpaCalculate(map)
            }

        }

    }

    private fun gpaCalculate(map: HashMap<Int, String>) {
        var totalGainedCredits = 0.0
        var totalCredit = 0.0
        var creditPoints: Double
        lifecycleScope.launch(Dispatchers.Main) {


            mapArray.clear()
            //retrieving map into arraylist
            try {
                for (i in 0 until map.size) {

                    val value = map[i]
                    mapArray.add(value!!)
                }
            } catch (e: Exception) {

               Toast.makeText(requireContext(),"Enter all the fields",Toast.LENGTH_SHORT).show()
            }

            if (mapArray.size != subjectList.size) {
                Toast.makeText(requireContext(), "All Fields Are Required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //Retrieving Credits
                val credits = ArrayList<String>()
                for (i in subjectList.indices) {
                    val creditsRetrieve: String = java.lang.String.valueOf(subjectList[i].credits)
                    credits.add(creditsRetrieve)
                }
                try {


                    //For Total Credits
                    for (i in credits.indices) {
                        val value = credits[i].toDouble()
                        totalCredit += value
                    }
                    Log.i("totalCredit=", totalCredit.toString())
                    for (i in credits.indices) {
                        creditPoints = credits[i].toDouble() * mapArray.get(i).toDouble()
                        totalGainedCredits += creditPoints
                    }
                    val sgpa: Double = totalGainedCredits / totalCredit
                    val result = sgpa.toString()
                    binding.gpaResultTv.text = result.substring(0, 4)
                } catch (e: Exception) {
                    if (e.message == "empty String") {
                        Toast.makeText(
                            requireContext(),
                            "All Fields are Required",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    e.message
                }
            }
        }
    }


    private fun creditSystem(branch: String, sem: Int) {
        if (sem == 6 || sem == 7) {
//            content.setVisibility(View.INVISIBLE)
//            updateText.setVisibility(View.VISIBLE)
        } else {
            lifecycleScope.launch(Dispatchers.IO) {


                if (branch == "cse") {
                    when (sem) {
                        0 -> {
                            subjectList.add(GpaModel("Mathematics-1", 4.0))
                            subjectList.add(GpaModel("Chemistry", 4.0))
                            subjectList.add(GpaModel("BEE", 3.0))
                            subjectList.add(GpaModel("Engg Workshop", 2.0))
                            subjectList.add(GpaModel("English", 2.0))
                            subjectList.add(GpaModel("Engg Chemistry Lab", 1.5))
                            subjectList.add(GpaModel("Elcs Lab", 1.0))
                            subjectList.add(GpaModel("BEE Lab", 1.0))
                        }
                        1 -> {
                            subjectList.add(GpaModel("Mathematics-2", 4.0))
                            subjectList.add(GpaModel("Applied Physics", 4.0))
                            subjectList.add(GpaModel("PPS", 4.0))
                            subjectList.add(GpaModel("Engineering Graphics", 3.0))
                            subjectList.add(GpaModel("AP Lab", 1.5))
                            subjectList.add(GpaModel("PPS Lab", 1.5))
                            subjectList.add(GpaModel("EVS", 0.0))
                        }
                        2 -> {
                            subjectList.add(GpaModel("Ade", 3.0))
                            subjectList.add(GpaModel("Data Structures", 4.0))
                            subjectList.add(GpaModel("Cosm", 4.0))
                            subjectList.add(GpaModel("Coa", 3.0))
                            subjectList.add(GpaModel("C++", 2.0))
                            subjectList.add(GpaModel("Ade Lab", 1.0))
                            subjectList.add(GpaModel("Ds Lab", 1.5))
                            subjectList.add(GpaModel("IT workshop", 1.5))
                            subjectList.add(GpaModel("C++ Lab", 1.0))
                            subjectList.add(GpaModel("Gs Lab", 0.0))
                        }
                        3 -> {
                            subjectList.add(GpaModel("Dm", 3.0))
                            subjectList.add(GpaModel("Befa", 3.0))
                            subjectList.add(GpaModel("Operating Systems", 3.0))
                            subjectList.add(GpaModel("Dbms", 4.0))
                            subjectList.add(GpaModel("Java Programming", 4.0))
                            subjectList.add(GpaModel("O.s Lab", 1.5))
                            subjectList.add(GpaModel("Dbms Lab", 1.5))
                            subjectList.add(GpaModel("Java Programming", 1.0))
                            subjectList.add(GpaModel("COI", 0.0))
                        }
                        4 -> {
                            subjectList.add(GpaModel("FLAT", 3.0))
                            subjectList.add(GpaModel("Software Engineering", 3.0))
                            subjectList.add(GpaModel("Computer Networks", 3.0))
                            subjectList.add(GpaModel("Web Technologies", 3.0))
                            subjectList.add(GpaModel("Professional Elective-I", 3.0))
                            subjectList.add(GpaModel("Professional Elective-II", 3.0))
                            subjectList.add(GpaModel("Software Engineering Lab", 1.5))
                            subjectList.add(GpaModel("CN & Wt", 1.5))
                            subjectList.add(GpaModel("ACSL", 1.0))
                            subjectList.add(GpaModel("IPR", 0.0))
                        }
                        5 -> {
                            subjectList.add(GpaModel("Machine Learning", 4.0))
                            subjectList.add(GpaModel("Compiler Design", 4.0))
                            subjectList.add(GpaModel("Design and Analysis of Algorithms", 4.0))
                            subjectList.add(GpaModel("Professional Elective – III ", 3.0))
                            subjectList.add(GpaModel("Open Elective-I", 3.0))
                            subjectList.add(GpaModel("Machine Learning Lab", 1.5))
                            subjectList.add(GpaModel("Compiler Design Lab ", 1.5))
                            subjectList.add(GpaModel("Professional Elective-III Lab", 1.0))
                        }
                    }
                }
                if (branch == "eee") {
                    when (sem) {
                        0 -> {
                            subjectList.add(GpaModel("Mathematics-1", 4.0))
                            subjectList.add(GpaModel("Chemistry", 4.0))
                            subjectList.add(GpaModel("BEE", 3.0))
                            subjectList.add(GpaModel("Engg Workshop", 2.5))
                            subjectList.add(GpaModel("English", 2.0))
                            subjectList.add(GpaModel("Engg Chemistry Lab", 1.5))
                            subjectList.add(GpaModel("Elcs Lab", 1.0))
                            subjectList.add(GpaModel("BEE Lab", 1.0))
                        }
                        1 -> {
                            subjectList.add(GpaModel("Mathematics-2", 4.0))
                            subjectList.add(GpaModel("Applied Physics", 4.0))
                            subjectList.add(GpaModel("PPS", 4.0))
                            subjectList.add(GpaModel("Engineering Graphics", 3.0))
                            subjectList.add(GpaModel("AP Lab", 1.5))
                            subjectList.add(GpaModel("PPS Lab", 1.5))
                            subjectList.add(GpaModel("EVS", 0.0))
                        }
                        2 -> {
                            subjectList.add(GpaModel("Engineering Mechanics", 4.0))
                            subjectList.add(GpaModel("Electrical Circuit Analysis", 4.0))
                            subjectList.add(GpaModel("Analog Electronics", 3.0))
                            subjectList.add(GpaModel("Electrical Machines - I ", 4.0))
                            subjectList.add(GpaModel("Electromagnetic Fields ", 3.0))
                            subjectList.add(GpaModel("Electrical Machines Lab - I ", 1.0))
                            subjectList.add(GpaModel("Analog Electronics Lab", 1.0))
                            subjectList.add(GpaModel("Electrical Circuits Lab", 1.0))
                            subjectList.add(GpaModel("Gender Sensitization Lab", 0.0))
                        }
                        3 -> {
                            subjectList.add(GpaModel("Laplace Transforms", 4.0))
                            subjectList.add(GpaModel("Electrical Machines – II", 4.0))
                            subjectList.add(GpaModel("Digital Electronics", 3.0))
                            subjectList.add(GpaModel("Control Systems", 4.0))
                            subjectList.add(GpaModel("Power System - I", 3.0))
                            subjectList.add(GpaModel("Digital Electronics Lab", 1.0))
                            subjectList.add(GpaModel("Electrical Machines Lab - II", 1.0))
                            subjectList.add(GpaModel("Control Systems Lab", 1.0))
                        }
                        4 -> {
                            subjectList.add(GpaModel("Power Electronics", 4.0))
                            subjectList.add(GpaModel("Power System-II ", 4.0))
                            subjectList.add(GpaModel("Measurements and Instrumentation", 4.0))
                            subjectList.add(GpaModel("Professional Elective-I", 3.0))
                            subjectList.add(GpaModel("BEFA", 3.0))
                            subjectList.add(GpaModel("Power System Simulation Lab", 1.0))
                            subjectList.add(GpaModel("Power Electronics Lab", 1.0))
                            subjectList.add(GpaModel("Measurements and Instrumentation Lab", 1.0))
                            subjectList.add(GpaModel("Advanced Communication Skills Lab", 1.0))
                        }
                        5 -> {
                            subjectList.add(GpaModel("Open Elective-I", 3.0))
                            subjectList.add(GpaModel("Professional Elective-II", 3.0))
                            subjectList.add(GpaModel("Signals and Systems", 3.0))
                            subjectList.add(GpaModel("Microprocessors & Microcontrollers", 3.0))
                            subjectList.add(GpaModel("Power System Protection", 4.0))
                            subjectList.add(GpaModel("Power System Operation and Control", 3.0))
                            subjectList.add(GpaModel("Power System Lab", 1.0))
                            subjectList.add(GpaModel("Microprocessors & Microcontrollers Lab", 1.0))
                            subjectList.add(GpaModel("Signals and Systems Lab", 1.0))
                        }
                    }
                }
                if (branch == "it") {
                    when (sem) {
                        0 -> {
                            subjectList.add(GpaModel("Mathematics-1", 4.0))
                            subjectList.add(GpaModel("Chemistry", 4.0))
                            subjectList.add(GpaModel("BEE", 3.0))
                            subjectList.add(GpaModel("Engg Workshop", 2.5))
                            subjectList.add(GpaModel("English", 2.0))
                            subjectList.add(GpaModel("Engg Chemistry Lab", 1.5))
                            subjectList.add(GpaModel("Elcs Lab", 1.0))
                            subjectList.add(GpaModel("BEE Lab", 1.0))
                        }
                        1 -> {
                            subjectList.add(GpaModel("Mathematics-2", 4.0))
                            subjectList.add(GpaModel("Applied Physics", 4.0))
                            subjectList.add(GpaModel("PPS", 4.0))
                            subjectList.add(GpaModel("Engineering Graphics", 3.0))
                            subjectList.add(GpaModel("AP Lab", 1.5))
                            subjectList.add(GpaModel("PPS Lab", 1.5))
                            subjectList.add(GpaModel("EVS", 0.0))
                        }
                        2 -> {
                            subjectList.add(GpaModel("Analog and Digital Electronics", 3.0))
                            subjectList.add(GpaModel("Data Structures", 4.0))
                            subjectList.add(GpaModel("COSM", 4.0))
                            subjectList.add(GpaModel("COM", 3.0))
                            subjectList.add(GpaModel("C++", 2.0))
                            subjectList.add(GpaModel("ADE Lab", 1.0))
                            subjectList.add(GpaModel("D.S Lab", 1.5))
                            subjectList.add(GpaModel("IT Workshop", 1.5))
                            subjectList.add(GpaModel("C++ Lab", 1.0))
                            subjectList.add(GpaModel("Gender sensitization Lab", 0.0))
                        }
                        3 -> {
                            subjectList.add(GpaModel("Discrete Mathematics", 3.0))
                            subjectList.add(GpaModel("BEFA", 3.0))
                            subjectList.add(GpaModel("Operating Systems", 3.0))
                            subjectList.add(GpaModel("DBMS", 4.0))
                            subjectList.add(GpaModel("Java Programming", 4.0))
                            subjectList.add(GpaModel("Operating Systems Lab", 1.5))
                            subjectList.add(GpaModel("DBMS Lab", 1.5))
                            subjectList.add(GpaModel("Java Programming Lab", 1.0))
                            subjectList.add(GpaModel("Constitution of India", 0.0))
                        }
                        4 -> {
                            subjectList.add(GpaModel("FLAT", 3.0))
                            subjectList.add(GpaModel("Software Engineering", 3.0))
                            subjectList.add(GpaModel("Data Communication & Computer Networks", 4.0))
                            subjectList.add(GpaModel("Web Programming", 2.0))
                            subjectList.add(GpaModel("Professional Elective - I", 3.0))
                            subjectList.add(GpaModel("Professional Elective - II", 3.0))
                            subjectList.add(GpaModel("Software Engineering Lab", 1.5))
                            subjectList.add(GpaModel("CN & WP Lab", 1.5))
                            subjectList.add(GpaModel("Advanced Communication Skills Lab", 1.0))
                            subjectList.add(GpaModel("Intellectual Property Rights", 0.0))
                        }
                        5 -> {
                            subjectList.add(GpaModel("Intro to Embedded Systems", 3.0))
                            subjectList.add(GpaModel("Principles of Compiler Construction", 3.0))
                            subjectList.add(GpaModel("Algorithm Design and Analysis", 3.0))
                            subjectList.add(GpaModel("Internet of Things", 3.0))
                            subjectList.add(GpaModel("Professional Elective –III", 3.0))
                            subjectList.add(GpaModel("Open Elective-I", 3.0))
                            subjectList.add(GpaModel("ES & IOT Lab", 3.0))
                            subjectList.add(GpaModel("Compiler Construction Lab", 3.0))
                            subjectList.add(GpaModel("Professional Elective-III Lab", 3.0))
                        }
                    }
                }
                if (branch == "ece") {
                    when (sem) {
                        0 -> {
                            subjectList.add(GpaModel("Mathematics-1", 4.0))
                            subjectList.add(GpaModel("Applied Physics", 4.0))
                            subjectList.add(GpaModel("PPS", 4.0))
                            subjectList.add(GpaModel("Engg Graphics", 3.0))
                            subjectList.add(GpaModel("AP Lab", 1.5))
                            subjectList.add(GpaModel("PPS Lab", 1.5))
                            subjectList.add(GpaModel("EVS", 0.0))
                        }
                        1 -> {
                            subjectList.add(GpaModel("Mathematics-2", 4.0))
                            subjectList.add(GpaModel("Chemistry", 4.0))
                            subjectList.add(GpaModel("BEE", 3.0))
                            subjectList.add(GpaModel("Engg Workshop", 2.5))
                            subjectList.add(GpaModel("English", 2.0))
                            subjectList.add(GpaModel("Engg Chem Lab", 1.5))
                            subjectList.add(GpaModel("Elcs Lab", 1.0))
                            subjectList.add(GpaModel("BEE Lab", 1.0))
                        }
                        2 -> {
                            subjectList.add(GpaModel("Electronic Devices and Circuits", 4.0))
                            subjectList.add(GpaModel("Network Analysis and Transmission Lines", 3.0))
                            subjectList.add(GpaModel("Digital System Design ", 4.0))
                            subjectList.add(GpaModel("Signals and Systems", 4.0))
                            subjectList.add(
                                GpaModel(
                                    "Probability Theory and Stochastic Processes",
                                    3.0
                                )
                            )
                            subjectList.add(GpaModel("Electronic Devices and Circuits Lab", 1.0))
                            subjectList.add(GpaModel("Digital System Design Lab", 1.0))
                            subjectList.add(GpaModel("Basic Simulation Lab", 1.0))
                            subjectList.add(GpaModel("Constitution of India", 0.0))
                        }
                        3 -> {
                            subjectList.add(GpaModel("Laplace Transforms", 4.0))
                            subjectList.add(GpaModel("Electromagnetic Fields and Waves", 3.0))
                            subjectList.add(GpaModel("Analog and Digital Communications", 4.0))
                            subjectList.add(GpaModel("Linear IC Applications", 3.0))
                            subjectList.add(GpaModel("Electronic Circuit Analysis", 3.0))
                            subjectList.add(GpaModel("Analog and Digital Communications Lab", 1.5))
                            subjectList.add(GpaModel("IC Applications Lab", 1.5))
                            subjectList.add(GpaModel("Electronic Circuit Analysis Lab", 1.0))
                        }
                        4 -> {
                            subjectList.add(GpaModel("Microprocessors & Microcontrollers", 4.0))
                            subjectList.add(GpaModel("Data Communications and Networks", 4.0))
                            subjectList.add(GpaModel("Control Systems", 4.0))
                            subjectList.add(GpaModel("BEFA", 3.0))
                            subjectList.add(GpaModel("Professional Elective - I", 3.0))
                            subjectList.add(GpaModel("Microprocessors & Microcontrollers Lab", 1.5))
                            subjectList.add(GpaModel("Data Communications and Networks Lab", 1.5))
                            subjectList.add(GpaModel("Advanced Communication Skills Lab", 1.0))
                            subjectList.add(GpaModel("Intellectual Property Rights", 0.0))
                        }
                        5 -> {
                            subjectList.add(GpaModel("Antennas and Propagation", 4.0))
                            subjectList.add(GpaModel("Digital Signal Processing", 4.0))
                            subjectList.add(GpaModel("VLSI Design", 4.0))
                            subjectList.add(GpaModel("Professional Elective - II", 3.0))
                            subjectList.add(GpaModel("Open Elective - I ", 3.0))
                            subjectList.add(GpaModel("Digital Signal Processing Lab", 1.5))
                            subjectList.add(GpaModel("e – CAD Lab ", 1.5))
                            subjectList.add(GpaModel("Scripting Languages Lab", 1.0))
                            subjectList.add(GpaModel("Environmental Science", 0.0))
                        }
                    }
                }
                if (branch == "mech") {
                    if (sem == 0) {
                        subjectList.add(GpaModel("Mathematics - I", 4.0))
                        subjectList.add(GpaModel("Engineering Physics", 4.0))
                        subjectList.add(GpaModel("PPS", 4.0))
                        subjectList.add(GpaModel("Engineering Graphics", 3.0))
                        subjectList.add(GpaModel("Engineering Physics Lab", 1.5))
                        subjectList.add(GpaModel("PPS Lab", 1.5))
                        subjectList.add(GpaModel("Environmental Science", 0.0))
                    }
                    if (sem == 1) {
                        subjectList.add(GpaModel("Mathematics - II", 4.0))
                        subjectList.add(GpaModel("Chemistry", 4.0))
                        subjectList.add(GpaModel("Engineering Mechanics", 4.0))
                        subjectList.add(GpaModel("Engineering Workshop", 2.5))
                        subjectList.add(GpaModel("English", 2.0))
                        subjectList.add(GpaModel("Engineering Chemistry Lab", 1.5))
                        subjectList.add(GpaModel("ELCS Lab", 1.0))
                    }
                    if (sem == 2) {
                        subjectList.add(GpaModel("Probability and Statistics", 4.0))
                        subjectList.add(GpaModel("Mechanics of Solids", 4.0))
                        subjectList.add(GpaModel("Material Science and Metallurgy", 3.0))
                        subjectList.add(GpaModel("Production Technology", 3.0))
                        subjectList.add(GpaModel("Thermodynamics", 4.0))
                        subjectList.add(GpaModel("Production Technology Lab", 1.0))
                        subjectList.add(GpaModel("Machine Drawing Practice", 1.0))
                        subjectList.add(
                            GpaModel(
                                "Material Science and Mechanics of Solids Lab",
                                1.0
                            )
                        )
                        subjectList.add(GpaModel("Constitution of India", 0.0))
                    }
                    if (sem == 3) {
                        subjectList.add(GpaModel("BEE", 3.0))
                        subjectList.add(GpaModel("Kinematics of Machinery", 4.0))
                        subjectList.add(GpaModel("Thermal Engineering - I", 4.0))
                        subjectList.add(GpaModel("Fluid Mechanics and Hydraulic Machines", 4.0))
                        subjectList.add(GpaModel("Instrumentation and Control Systems", 3.0))
                        subjectList.add(GpaModel("BEE Lab", 1.0))
                        subjectList.add(GpaModel("Fluid Mechanics and Hydraulic Machines Lab", 1.0))
                        subjectList.add(GpaModel("Instrumentation and Control Systems Lab", 1.0))
                        subjectList.add(GpaModel("Gender Sensitization Lab", 0.0))
                    }
                    if (sem == 4) {
                        subjectList.add(GpaModel("Dynamics of Machinery", 4.0))
                        subjectList.add(GpaModel("Design of Machine Members-I", 3.0))
                        subjectList.add(GpaModel("Metrology & Machine Tools", 3.0))
                        subjectList.add(GpaModel("BEFA", 3.0))
                        subjectList.add(GpaModel("Thermal Engineering-II", 3.0))
                        subjectList.add(GpaModel("Operations Research", 3.0))
                        subjectList.add(GpaModel("Thermal Engineering Lab", 1.0))
                        subjectList.add(GpaModel("Metrology & Machine Tools Lab", 1.0))
                        subjectList.add(GpaModel("Kinematics & Dynamics Lab", 1.0))
                        subjectList.add(GpaModel("Intellectual Property Rights", 0.0))
                    }
                    if (sem == 5) {
                        subjectList.add(GpaModel("Design of Machine Members-II", 3.0))
                        subjectList.add(GpaModel("Heat Transfer", 4.0))
                        subjectList.add(GpaModel("CAD & CAM", 3.0))
                        subjectList.add(GpaModel("Professional Elective - I", 3.0))
                        subjectList.add(GpaModel("Open Elective - I", 3.0))
                        subjectList.add(GpaModel("Finite Element Methods", 3.0))
                        subjectList.add(GpaModel("Heat Transfer Lab", 1.0))
                        subjectList.add(GpaModel("CAD & CAM Lab", 1.0))
                        subjectList.add(GpaModel("Advanced Communication Skills lab", 1.0))
                        subjectList.add(GpaModel("Environmental Science", 0.0))
                    }
                }
                if (branch == "civil") {
                    if (sem == 0) {
                        subjectList.add(GpaModel("Mathematics - I", 4.0))
                        subjectList.add(GpaModel("Engineering Physics", 4.0))
                        subjectList.add(GpaModel("PPS", 4.0))
                        subjectList.add(GpaModel("Engineering Graphics", 3.0))
                        subjectList.add(GpaModel("Engineering Physics Lab", 1.5))
                        subjectList.add(GpaModel("PPS Lab", 1.5))
                        subjectList.add(GpaModel("Environmental Science", 0.0))
                    }
                    if (sem == 1) {
                        subjectList.add(GpaModel("Mathematics - II", 4.0))
                        subjectList.add(GpaModel("Chemistry", 4.0))
                        subjectList.add(GpaModel("Engineering Mechanics", 4.0))
                        subjectList.add(GpaModel("Engineering Workshop", 2.5))
                        subjectList.add(GpaModel("English", 2.0))
                        subjectList.add(GpaModel("Engineering Chemistry Lab", 1.5))
                        subjectList.add(GpaModel("ELCS Lab", 1.0))
                    }
                    if (sem == 2) {
                        subjectList.add(GpaModel("Surveying and Geomatics", 3.0))
                        subjectList.add(GpaModel("Engineering Geology", 2.0))
                        subjectList.add(GpaModel("Strength of Materials - I", 4.0))
                        subjectList.add(GpaModel("Probability and Statistics", 4.0))
                        subjectList.add(GpaModel("Fluid Mechanics", 4.0))
                        subjectList.add(GpaModel("Surveying Lab", 1.5))
                        subjectList.add(GpaModel("Strength of Materials Lab", 1.5))
                        subjectList.add(GpaModel("Engineering Geology Lab", 1.0))
                        subjectList.add(GpaModel("Constitution of India", 0.0))
                    }
                    if (sem == 3) {
                        subjectList.add(GpaModel("BEE", 3.0))
                        subjectList.add(GpaModel("BME", 2.0))
                        subjectList.add(
                            GpaModel(
                                "Building Materials,Construction and Planning",
                                3.0
                            )
                        )
                        subjectList.add(GpaModel("Strength of Materials - II", 3.0))
                        subjectList.add(GpaModel("Hydraulics and Hydraulic Machinery", 3.0))
                        subjectList.add(GpaModel("Structural Analysis - I", 3.0))
                        subjectList.add(GpaModel("Computer aided Civil Engineering Drawing", 1.5))
                        subjectList.add(GpaModel("BEE Lab", 1.0))
                        subjectList.add(GpaModel("Hydraulics and Hydraulic Machinery Lab ", 1.5))
                        subjectList.add(GpaModel("Gender Sensitization Lab", 0.0))
                    }
                    if (sem == 4) {
                        subjectList.add(GpaModel("Structural Analysis-II", 3.0))
                        subjectList.add(GpaModel("Geotechnical Engineering", 3.0))
                        subjectList.add(GpaModel("Structural Engineering –I (RCC)", 4.0))
                        subjectList.add(GpaModel("Transportation Engineering", 3.0))
                        subjectList.add(GpaModel("Professional Elective-I", 3.0))
                        subjectList.add(GpaModel("Engineering Economics and Accountancy", 2.0))
                        subjectList.add(GpaModel("HE & CT Lab", 1.5))
                        subjectList.add(GpaModel("Geotechnical Engineering Lab", 1.5))
                        subjectList.add(GpaModel("Advanced Communication Skills Lab", 1.0))
                        subjectList.add(GpaModel("Intellectual Property Rights", 0.0))
                    }
                    if (sem == 5) {
                        subjectList.add(GpaModel("Hydrology & Water Resources Engineering", 4.0))
                        subjectList.add(GpaModel("Environmental Engineering", 3.0))
                        subjectList.add(GpaModel("Foundation Engineering", 3.0))
                        subjectList.add(GpaModel("Structural Engineering –II (Steel)", 4.0))
                        subjectList.add(GpaModel("Professional Elective –II", 3.0))
                        subjectList.add(GpaModel("Open Elective –I", 3.0))
                        subjectList.add(GpaModel("Environmental Engineering Lab", 1.0))
                        subjectList.add(GpaModel("Computer Aided Design Lab", 1.0))
                        subjectList.add(GpaModel("Environmental Science", 0.0))
                    }
                }
            }
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(value: HashMap<Int, String>) {
        map = value
    }


}