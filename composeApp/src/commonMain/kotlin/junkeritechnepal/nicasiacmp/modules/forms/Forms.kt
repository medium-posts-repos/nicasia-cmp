package junkeritechnepal.nicasiacmp.modules.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import junkeritechnepal.nicasiacmp.modules.menus.MenuDataSource

object Forms {
    @Composable
    fun MenuCardListView() {
        val dataSource = MenuDataSource.paymentSubMenus
        LazyColumn(modifier = Modifier) {
            items(dataSource.count()) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = dataSource[item].name ?: "",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = dataSource[item].desc ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}