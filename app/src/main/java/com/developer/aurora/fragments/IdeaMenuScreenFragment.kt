package com.developer.aurora.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.developer.aurora.R
import com.developer.aurora.adapters.IdeaRecyclerAdapter
import com.developer.aurora.databinding.FragmentIdeaMenuScreenBinding
import com.developer.aurora.models.IdeaModel
import com.google.firebase.database.*


class IdeaMenuScreenFragment : Fragment() {
    private var _binding: FragmentIdeaMenuScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var ideaList: ArrayList<IdeaModel>
    private lateinit var adapter: IdeaRecyclerAdapter
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIdeaMenuScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ideaList = ArrayList()

        adapter = IdeaRecyclerAdapter(ideaList)

        binding.ideasRecyclerView.adapter = adapter
        initIdea()
        binding.ideasRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.addBtn.hide()


                } else {
                    binding.addBtn.show()

                }


                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        binding.addBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_ideaMenuScreenFragment_to_uploadIdeaFragment)
        }

    }

    private fun initIdea() {
        binding.loaderLayout.visibility = View.VISIBLE

        reference = FirebaseDatabase.getInstance().reference
        reference.child("Ideas").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1: DataSnapshot in snapshot.children) {
                    val value = snapshot1.getValue(IdeaModel::class.java)
                    ideaList.add(value!!)

                }
                ideaList.reverse()
                try {


                    binding.loaderLayout.visibility = View.GONE
                } catch (e: Exception) {
                    Log.d("e:", e.message.toString())
                }
adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.loaderLayout.visibility = View.GONE
                Toast.makeText(requireActivity(), error.message.toString(), Toast.LENGTH_SHORT)
                    .show()

            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}