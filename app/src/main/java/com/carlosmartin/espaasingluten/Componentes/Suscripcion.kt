package com.carlosmartin.espaasingluten.Componentes

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.carlosmartin.espaasingluten.R
import com.google.common.collect.ImmutableList

class Suscripcion : AppCompatActivity() {

    private lateinit var billingClient: BillingClient
    private var myList: List<ProductDetails> = emptyList()
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suscripcion)

        val btnMensual: Button = findViewById(R.id.buttonMensual)

        btnMensual.setOnClickListener {
            val productDetailsParamsList = listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                    .setProductDetails(myList.get(0))
                    .build()
            )

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

            val billingResult = billingClient.launchBillingFlow(this, billingFlowParams)
        }

        // Configurar el BillingClient
        billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        startConnection()

    }

    // Implementar el listener para manejar las respuestas de la compra
    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                // La compra se ha realizado con éxito, aquí puedes realizar alguna acción después de la compra
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
                // El usuario ha cancelado la compra
            } else {
                // Hubo un error en la compra
            }
        }


    // Implementar la lógica para iniciar la compra
    private fun startConnection() {
        // Conectar al servicio de facturación
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    queryProduct()
                }
            }

            override fun onBillingServiceDisconnected() {
                startConnection()
            }
        })
    }
    private fun queryProduct() {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("suscripcionmensual")
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()))
            .build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && productDetailsList.isNotEmpty()) {
                myList = productDetailsList
            }
        }
    }


}


