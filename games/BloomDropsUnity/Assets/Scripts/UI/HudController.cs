using System.Collections.Generic;
using BloomDrops.Data;
using UnityEngine;
using UnityEngine.UI;

namespace BloomDrops.UI
{
    /// <summary>
    /// Uses Unity UI Text when TMP is unavailable — dual support via optional refs.
    /// </summary>
    public class HudController : MonoBehaviour
    {
        public Text levelText;
        public Text movesText;
        public Text scoreText;
        public Text hintText;
        public Transform goalsRoot;
        public GoalChip goalChipPrefab;

        public void SetLevel(int level)
        {
            if (levelText) levelText.text = level.ToString();
        }

        public void SetMoves(int moves)
        {
            if (movesText) movesText.text = moves.ToString();
        }

        public void SetScore(int score)
        {
            if (scoreText) scoreText.text = score.ToString();
        }

        public void FlashHint(string msg)
        {
            if (hintText) hintText.text = msg;
        }

        public void SetGoals(Dictionary<EssenceId, int> goals, Dictionary<EssenceId, int> collected, EssenceCatalog catalog)
        {
            if (goalsRoot == null || goalChipPrefab == null) return;
            for (int i = goalsRoot.childCount - 1; i >= 0; i--)
                Destroy(goalsRoot.GetChild(i).gameObject);

            foreach (var kv in goals)
            {
                collected.TryGetValue(kv.Key, out int got);
                var chip = Instantiate(goalChipPrefab, goalsRoot);
                var def = catalog.Get(kv.Key);
                chip.Setup(def != null ? def.sprite : null, def != null ? def.color : Color.white, got, kv.Value);
            }
        }
    }
}
