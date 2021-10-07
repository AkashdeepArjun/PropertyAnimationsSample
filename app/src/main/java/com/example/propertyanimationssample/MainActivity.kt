package com.example.propertyanimationssample

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.example.propertyanimationssample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpListeners()
    }

    fun setUpListeners(){

        binding.rotateButton.setOnClickListener {
            rotate(binding.target)
        }
        binding.translateButton.setOnClickListener {
            translate_X(binding.target)
        }

        binding.scaleButton.setOnClickListener {
            scale(binding.target)
        }

        binding.fadeButton.setOnClickListener {
            fade(binding.target)
        }

        binding.colorButton.setOnClickListener {
            colorise(binding.target)
        }

        binding.showerButton.setOnClickListener {

            rain(binding.target)
        }


    }


    fun rotate( v:View){
        val rotate_animator=ObjectAnimator.ofFloat(v,View.ROTATION,-360f,0f)
        rotate_animator.duration=1000
        rotate_animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                binding.rotateButton.isEnabled=false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                binding.rotateButton.isEnabled=true
            }
        })
        rotate_animator.start()

    }

    fun translate_X(v:View){
        val translate_animator=ObjectAnimator.ofFloat(v,View.TRANSLATION_X,200f)
        translate_animator.repeatCount=1
        translate_animator.repeatMode=ObjectAnimator.REVERSE
        translate_animator.duration=1000
        translate_animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                binding.translateButton.isEnabled=false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                binding.translateButton.isEnabled=true
            }
        })
        translate_animator.start()
    }

    fun scale(v:View){
        val scaleX=PropertyValuesHolder.ofFloat(View.SCALE_X,4f)
        val scaleY=PropertyValuesHolder.ofFloat(View.SCALE_Y,4f)
        val scale_animator=ObjectAnimator.ofPropertyValuesHolder(v,scaleX,scaleY)
        scale_animator.repeatCount=1
        scale_animator.repeatMode=ObjectAnimator.REVERSE
        scale_animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                binding.scaleButton.isEnabled=false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                binding.scaleButton.isEnabled=true
            }
        })
        scale_animator.duration=1000
        scale_animator.start()

    }

    fun fade(v:View){
        val fade_animator=ObjectAnimator.ofFloat(v,View.ALPHA,0f)
        fade_animator.duration=1000
        fade_animator.repeatCount=1
        fade_animator.repeatMode=ObjectAnimator.REVERSE
        fade_animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                binding.fadeButton.isEnabled=false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                binding.fadeButton.isEnabled=true
            }
        })
        fade_animator.start()
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun colorise(v:View){

        val color_animator=ObjectAnimator.ofArgb(v.parent,"backgroundColor",Color.BLACK,Color.RED)
        color_animator.duration=1000
        color_animator.repeatCount=1
        color_animator.repeatMode=ObjectAnimator.REVERSE
        color_animator.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                binding.colorButton.isEnabled=false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                binding.colorButton.isEnabled=true
            }
        })
        color_animator.start()
    }

    fun rain(v:View){

        val container=v.parent as ViewGroup
        val container_width=container.width
        val container_height=container.height
        var target_width=v.width.toFloat()
        var target_height=v.height.toFloat()
        val new_star=AppCompatImageView(this)
        new_star.setImageResource(R.drawable.ic_baseline_star_24)
        new_star.layoutParams=FrameLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        container.addView(new_star)
        new_star.scaleX=Math.random().toFloat()*1.5f
        new_star.scaleY=new_star.scaleX
        target_width*=new_star.scaleX
        target_height*=new_star.scaleY

        //TRANSLATION
        val mover=ObjectAnimator.ofFloat(new_star,View.TRANSLATION_Y,-target_height,container_height+target_height)
        mover.interpolator=AccelerateDecelerateInterpolator()

        new_star.translationX=Math.random().toFloat()*container_width-target_width/2

        //ROTATOR
        val rotator=ObjectAnimator.ofFloat(new_star,View.ROTATION,(Math.random()*1080).toFloat())

        rotator.interpolator=LinearInterpolator()
        
        val set=AnimatorSet()
        set.playTogether(mover,rotator)
        set.duration=((Math.random()*1500+500)).toLong()
        set.addListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                container.removeView(new_star)
            }
        })
        set.start()

    }

}