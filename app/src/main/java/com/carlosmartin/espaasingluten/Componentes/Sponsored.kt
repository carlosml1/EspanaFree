package com.carlosmartin.espaasingluten.Componentes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.carlosmartin.espaasingluten.R


class Sponsored : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sponsored)

        ModoInversivo.setImmersiveMode(this)

        val almafilms: ImageButton = findViewById(R.id.almafilms)
        val singlutismo: ImageButton = findViewById(R.id.singlutismo)

        almafilms.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://almafilms.es/")
            startActivity(intent);
        }

        singlutismo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.singlutenismo.com/")
            startActivity(intent);
        }
    }
}