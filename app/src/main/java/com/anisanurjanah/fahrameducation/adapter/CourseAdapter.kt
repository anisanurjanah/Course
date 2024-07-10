package com.anisanurjanah.fahrameducation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anisanurjanah.fahrameducation.data.Course
import com.anisanurjanah.fahrameducation.databinding.ItemCourseBinding

class CourseAdapter(
    private val data : ArrayList<Course>,
    val onClick : (Course) -> Unit
)
    : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CourseViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = data[position]
        holder.binding.root.setOnClickListener { onClick(course) }
        holder.binding.ivCourse.load(course.featuredImage)
        holder.binding.tvCourseTitle.text = course.title
        holder.binding.tvCourseExcerpt.text = course.excerpt
    }
}