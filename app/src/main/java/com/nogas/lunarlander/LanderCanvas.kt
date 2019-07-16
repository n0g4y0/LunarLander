package com.nogas.lunarlander

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import stanford.androidlib.graphics.*
import java.util.ArrayList

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

    private lateinit var rocketImage:Bitmap
    // esta variable, simula el fueguito
    private var rocketImageThrust = ArrayList<Bitmap>()

    private lateinit var rocket: GSprite
    private lateinit var moonSurface:GSprite

    /*
    * called when the GCanvas is being created
    * Sets up the SPRITE objects and adds them to the screen.
    * */

    override fun init() {
        // para DEBUGEAR, y ver cual es el funcionamiento de las librerias:
        //GSprite.setDebug(true)
        backgroundColor = GColor.BLACK

        // representa la imagen de la luna: (es una LIBRARY de android)
        var moonSurfaceImage = BitmapFactory.decodeResource(resources,R.drawable.moonsurface)
        // hacer que la imagen, sea mas pequeña:
        moonSurfaceImage = moonSurfaceImage.scaleToWidth(this.width.toFloat())

        moonSurface = GSprite(moonSurfaceImage)
        //la siguiente linea, situa la imagen, de la superficio lunar, al pie del ACTIVITY
        moonSurface.bottomY = this.height.toFloat()
        // le da un margen de colision, sobre la superficie lunar:
        moonSurface.collisionMarginTop = moonSurface.height / 4f

        add(moonSurface)

        // representa la imagen del cohete: (es una LIBRARY de android)
        rocketImage = BitmapFactory.decodeResource(resources,R.drawable.rocketship1)
        // hacer que la imagen, sea mas pequeña:
        rocketImage = rocketImage.scaleToWidth(this.width / 6f)

        // representa la imagen del cohete: (es una LIBRARY de android)
        var rocketImageThrust1 = BitmapFactory.decodeResource(resources,R.drawable.rocketshipthrust1)
        // hacer que la imagen, sea mas pequeña:
        rocketImageThrust1 = rocketImageThrust1.scaleToWidth(this.width / 6f)

        // representa la imagen del cohete: (es una LIBRARY de android)
        var rocketImageThrust2 = BitmapFactory.decodeResource(resources,R.drawable.rocketshipthrust2)
        // hacer que la imagen, sea mas pequeña:
        rocketImageThrust2 = rocketImageThrust2.scaleToWidth(this.width / 6f)

        // representa la imagen del cohete: (es una LIBRARY de android)
        var rocketImageThrust3 = BitmapFactory.decodeResource(resources,R.drawable.rocketshipthrust3)
        // hacer que la imagen, sea mas pequeña:
        rocketImageThrust3 = rocketImageThrust3.scaleToWidth(this.width / 6f)

        // representa la imagen del cohete: (es una LIBRARY de android)
        var rocketImageThrust4 = BitmapFactory.decodeResource(resources,R.drawable.rocketshipthrust4)
        // hacer que la imagen, sea mas pequeña:
        rocketImageThrust4 = rocketImageThrust4.scaleToWidth(this.width / 6f)

        rocketImageThrust.add(rocketImageThrust1)
        rocketImageThrust.add(rocketImageThrust2)
        rocketImageThrust.add(rocketImageThrust3)
        rocketImageThrust.add(rocketImageThrust4)


        rocket = GSprite(rocketImage)
        //rocket.velocityY = 10f
        rocket.accelerationY = GRAVITY_ACCELERATION
        add(rocket)

        // funcion, para controlar el TOUCH:

        setOnTouchListener { _, event ->
            handleTouchEvent(event)
            true
        }



    }

    private fun handleTouchEvent(event:MotionEvent){

        if (event.action == MotionEvent.ACTION_DOWN){
            // el usuario presiona hacia abajo:
            rocket.accelerationY = THRUST_ACCELERATION
            //imagen que simula el fuegito que sale del cohete
            rocket.bitmaps = rocketImageThrust
            rocket.framesPerBitmap = FRAMES_PER_SECOND / 3
        }else if (event.action == MotionEvent.ACTION_UP){
            //el usuario deja de apretar:
            rocket.accelerationY = GRAVITY_ACCELERATION
            rocket.bitmap = rocketImage
        }

    }

    private fun tick(){
        rocket.update()
        doCollisions()
    }

    private fun doCollisions(){
        // usando la libreria, si se colisiona con la superficie lunar , el cohete se para.
        if (rocket.collidesWith(moonSurface)){

            if (rocket.velocityY <= MAX_SAFE_LANDING_VELOCITY){
                //you win
//                var message = GLabel("Tu ganas")
//               message.fontSize = 100f
//                message.color = GColor.RED
//                add(message)
            }else{
                // you lose
                var message = GLabel("Perdiste :(")
                message.fontSize = 100f
                message.color = GColor.ORANGE
                add(message)
            }

            rocket.velocityY = 0f
            rocket.accelerationY = 0f
        }
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