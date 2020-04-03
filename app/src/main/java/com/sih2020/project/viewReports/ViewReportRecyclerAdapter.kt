package com.sih2020.project.viewReports

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sih2020.project.base.MainActivity
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.maps.MapsActivity
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.utility.Functions


class ViewReportRecyclerAdapter(
    private var datalist: ArrayList<Problem>,
    private var fragment: Fragment
) : RecyclerView.Adapter<ViewReportRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.view_reports_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = datalist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val problem = datalist[position]

        setValues(problem, holder)

        /*holder.itemView.setOnClickListener {
            remove(position)
        }*/

        holder.displayImage.setOnClickListener {

            /**
             * @author Lakshay Dutta
             * @since 24-01-20
             *
             * Image loader. Loads an Image provided in the Problem.imageID field.
             * Uses Glide library. Uses Dialog box to show the image.
             *
             * @see Problem
             * @see com.sih2020.project.constants.Constants.PROBLEM_IMAGEID
             *
             * set setCanceledOnTouchOutside = "false" to prevent dismiss on touch outside
             */

            val dialog = Dialog(MainActivity.getMainContext(), R.style.SlideInOut)
            dialog.setContentView(R.layout.image_loader_dialog)

            val imageLoadingProgress =
                dialog.findViewById<LottieAnimationView>(R.id.imageLoadingProgress)
            val imageLoaderView = dialog.findViewById<ImageView>(R.id.imageLoaderView)

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //dialog.setCanceledOnTouchOutside(false)
            dialog.show()

            Glide.with(fragment).load("${RestURLs.SERVER}/${problem.imageid}")
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageLoadingProgress.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(imageLoaderView)

        }

        /**
         * Displays given Problem.lat and Problem.lng coordinates on the map
         */
        holder.displayMap.setOnClickListener{
            val intent = Intent(fragment.context, MapsActivity::class.java)

            // Assign latitude and longitude from the problem to be passes into the Maps class
            intent.putExtra(Constants.PROBLEM_LATITUDE,problem.latitude)
            intent.putExtra(Constants.PROBLEM_LONGITUDE,problem.longitude)

            fragment.startActivity(intent)
        }

        /**
         * Upvote and didDownvote functionality
         * TODO : send /get requests for didUpvote and didDownvote
         */
//        holder.upvote.setOnClickListener {
//
//        }
//
//        holder.downvote.setOnClickListener {
//
//        }
    }

    private fun remove(position: Int) {
        datalist.removeAt(position)
        notifyDataSetChanged()
    }

//    private fun voteToggle(vote: Int, value: Int, holder: ViewHolder) {
//        /**
//         * vote -> 1 means upvote , -1 means downvote
//         * value -> 1 means did , -1 means undo
//         */
//        when (vote) {
//            1 -> {
//                if (value == 1) // did an upvote DO remove any downvotes
//                {
//                    holder.upvote.setBackgroundResource(R.drawable.didupvote)
//                    holder.downvote.setBackgroundResource(R.drawable.downvote)
//                } else // removed the upvote
//                    holder.upvote.setBackgroundResource(R.drawable.upvote)
//            }
//            -1 -> {
//                if (value == 1) // did a downvote DO remove any upvotes
//                {
//                    holder.downvote.setBackgroundResource(R.drawable.diddownvote)
//                    holder.upvote.setBackgroundResource(R.drawable.upvote)
//                } else // removed the downvote
//                    holder.downvote.setBackgroundResource(R.drawable.downvote)
//            }
//        }
//    }

    private fun setValues(problem: Problem, holder: ViewHolder) {
        holder.viewportsUpper.setBackgroundColor(Functions.getRandomMaterialColor())
        // holder.viewportsUpper.setBackgroundColor(Functions.getRes().getColor(R.color.greyishWhite))
        holder.description.text = problem.description
        holder.address.text = problem.address
        holder.landmark.text = problem.landmark
        holder.status.text = problem.status
//        holder.city.text = problem.city
        holder.date.text = problem.date
        holder.ward.text = problem.wardid
        holder._id.text = problem._id

        // See if the problem has some image URL
        if (problem.imageid.isEmpty())
            holder.displayImage.visibility = View.GONE

        // See if the problem has Lat-Lng Coordinates
        if (problem.latitude == 0.00 && problem.longitude == 0.00)
            holder.displayMap.visibility = View.GONE

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var viewportsUpper: LinearLayout = itemView.findViewById(R.id.viewReports_upper)
        var landmark: TextView = itemView.findViewById(R.id.landmark)
        var address: TextView = itemView.findViewById(R.id.address)
        var description: TextView = itemView.findViewById(R.id.description)
        var status: TextView = itemView.findViewById(R.id.status)
//        var city: TextView = itemView.findViewById(R.id.city)
        var date: TextView = itemView.findViewById(R.id.date)
        var displayImage: ImageView = itemView.findViewById(R.id.displayImage)
        var displayMap: ImageView = itemView.findViewById(R.id.displayMap)
        var ward:TextView = itemView.findViewById(R.id.ward)
        var _id:TextView = itemView.findViewById(R.id._id)

        // Added 25-01-2020
//        var upvote: ImageView = itemView.findViewById(R.id.upvote)
//        var downvote: ImageView = itemView.findViewById(R.id.downvote)

    }
}