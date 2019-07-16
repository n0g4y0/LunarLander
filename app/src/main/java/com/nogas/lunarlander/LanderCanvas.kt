package com.nogas.lunarlander

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.View
import stanford.androidlib.graphics.*

class LanderCanvas(context:Context, attrs:AttributeSet)
                :GCanvas(context,attrs){

    // static constants
    companion object {
        //frames per second of animation
        private const val FRAMES_PER_SECOND = 30

        //maximum velocity at which the rocket can hit the ground without
        private const val MAX_SAFE_LANDING_VELOCITY = 7.0f

        private const val ASTEROID_VELOCITY = -12.0f

        //downward acceleration due to gravity
        private const val GRAVITY_ACCELERATION = .5f

        //downward acceleration due to thrusters
        private const val THRUST_ACCELERATION = -.3f
    }

    // private fields:

    private lateinit var rocket: GSprite

    /*
    * called when the GCanvas is being created
    * Sets up the SPRITE objects and adds them to the screen.
    * */

    override fun init() {
        backgroundColor = GColor.BLACK

        //TODO
        //val rect = GRect(0f,0f,100f,100f)
        //rect.fillColor = GColor.RED

        // representa la imagen del cohete: (es una LIBRARY de android)
        var rocketImage = BitmapFactory.decodeResource(resources,R.drawable.rocketship1)
        // hacer que la imagen, sea mas pequeña:
        rocketImage = rocketImage.scaleToWidth(this.width / 10f)

        rocket = GSprite(rocketImage)
        //rocket.velocityY = 10f
        rocket.accelerationY = GRAVITY_ACCELERATION
        add(rocket)
    }

    private fun tick(){
        rocket.update()
    }

    /*
    * called when user clicks play game button
    * Starts a new game
    * */
    fun startGame(){
        animate(FRAMES_PER_SECOND){
            tick()
        }
    }

    fun stopGame(){
        animationStop()
    }



}