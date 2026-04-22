package com.spuldz.praksesprojekts.ui.screens.scores

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spuldz.praksesprojekts.R
import com.spuldz.praksesprojekts.core.database.entities.Score
import com.spuldz.praksesprojekts.ui.theme.LocalTheme
import com.spuldz.praksesprojekts.ui.theme.TextLg
import com.spuldz.praksesprojekts.ui.theme.TextMd
import com.spuldz.praksesprojekts.ui.theme.sizing

@Composable
fun ScoresScreen(
    viewModel: ScoresViewModel = hiltViewModel()
) {
    val theme = LocalTheme.current
    val scores by viewModel.scores.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getAllScores()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp30),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.scores),
                style = TextLg,
                color = theme.Text
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = sizing.dp10),
            verticalArrangement = Arrangement.spacedBy(sizing.dp10)
        ) {
            scores.forEachIndexed { index, score ->
                ScoreCard(
                    score,
                    index + 1
                )
            }
        }
    }
}

@Composable
fun ScoreCard(score: Score, place: Int) {
    val theme = LocalTheme.current

    val difficulty = when (score.difficulty) {
        "Easy" -> stringResource(R.string.easy)
        "Medium" -> stringResource(R.string.medium)
        "Hard" -> stringResource(R.string.medium)
        else -> stringResource(R.string.medium)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(sizing.dp64)
            .background(theme.BackgroundLighter)
            .padding(horizontal = sizing.dp10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = place.toString(),
                style = TextMd,
                color = theme.Text
            )
        }

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = DateUtils.formatElapsedTime(score.seconds),
                style = TextMd,
                color = theme.Text
            )
        }

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = difficulty,
                style = TextMd,
                color = theme.Text
            )
        }
    }
}
