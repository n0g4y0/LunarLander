package com.nogas.lunarlander

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_lander.*

class LanderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lander)
    }

    /*
    * Called when the play button is clicked.
    * Informs the canvas that we want to start the game.
    *
    * */

    fun playClick(view:View){
        mycanvas.startGame()
    }

    fun stopClick(view:View){
        mycanvas.stopGame()
    }


}
