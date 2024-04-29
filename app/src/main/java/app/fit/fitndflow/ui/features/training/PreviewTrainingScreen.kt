package app.fit.fitndflow.ui.features.training

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fit.fitndflow.R

@Composable
fun ImageDialogComponent(dismissDialog: () -> Unit, dialogTitle: String, @DrawableRes dialogImage: Int, dialogText: String, dialogCloseBtn: String) {
    MaterialTheme {
            AlertDialog(onDismissRequest = { dismissDialog() },

                        title = {
                            Column(
                                modifier = Modifier.padding(35.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(text = dialogTitle)
                                Spacer(modifier = Modifier.height(50.dp))

                                Image(
                                    painter = painterResource(id = dialogImage),
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp)
                                )
                            }
                        }, text = {
                    Text(
                        text = dialogText,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }, confirmButton = {
                    Button(
                        onClick = { dismissDialog() },
                        modifier = Modifier.background(color = Color.Transparent)
                            .clip(RoundedCornerShape(1.dp)),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 7.dp)
                    ) {
                        Text(text = dialogCloseBtn)
                    }
                })
        }
    }


@Preview
@Composable
fun PreviewMyPreview() {
    ImageDialogComponent({ }, "¡ ENHORABUENA !", R.drawable.record_image, "Has alcanzado un nuevo record ! ", "Cerrar")
}