package app.fit.fitndflow.ui.features.training

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.fit.fitndflow.ui.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.compose.primaryContainerLight


@Composable
fun ImageDialogComponent(
    dismissDialog: () -> Unit,
    dialogTitle: String,
    @DrawableRes dialogImage: Int,
    dialogText: String,
    dialogCloseBtn: String,
    @RawRes animationResource: Int? = null) {

    MaterialTheme {
        Box(contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier.padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = dialogTitle, style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(50.dp))

                Image(
                    painter = painterResource(id = dialogImage),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    text = dialogText,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 25.dp, bottom = 25.dp)
                )
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = primaryContainerLight),
                    onClick = { dismissDialog() },
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(1.dp)),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 7.dp)
                ) {
                    Text(text = dialogCloseBtn)
                }
            }
            animationResource?.let {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(it))
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(350.dp)
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewMyPreview() {
    ImageDialogComponent(
        { },
        "¡ ENHORABUENA !",
        R.drawable.record_image,
        "¡Has alcanzado un nuevo record ! ",
        "Cerrar"
    )
}