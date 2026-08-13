using System.Collections.Generic;
using BloomDrops.Data;
using BloomDrops.UI;
using UnityEngine;

namespace BloomDrops.Core
{
    public class GameManager : MonoBehaviour
    {
        [Header("Data")]
        public EssenceCatalog catalog;
        public LevelPack levelPack;

        [Header("Systems")]
        public BoardController board;
        public BoardInput input;
        public HudController hud;
        public ResultPopup resultPopup;

        int _levelIndex;
        int _moves;
        int _score;
        readonly Dictionary<EssenceId, int> _collected = new Dictionary<EssenceId, int>();
        readonly Dictionary<EssenceId, int> _goals = new Dictionary<EssenceId, int>();
        bool _levelOver;

        void OnEnable()
        {
            if (board != null)
            {
                board.OnCleared += HandleCleared;
                board.OnBoardSettled += HandleSettled;
            }
            if (resultPopup != null)
                resultPopup.OnContinue += HandleContinue;
        }

        void OnDisable()
        {
            if (board != null)
            {
                board.OnCleared -= HandleCleared;
                board.OnBoardSettled -= HandleSettled;
            }
            if (resultPopup != null)
                resultPopup.OnContinue -= HandleContinue;
        }

        void Start()
        {
            if (levelPack == null || levelPack.levels == null || levelPack.levels.Length == 0)
            {
                Debug.LogError("Bloom Drops: LevelPack missing. Run Bloom Drops > Setup Project.");
                return;
            }
            _score = 0;
            StartLevel(0);
        }

        public void RestartLevel()
        {
            StartLevel(_levelIndex);
        }

        public void StartLevel(int index)
        {
            _levelOver = false;
            _levelIndex = Mathf.Clamp(index, 0, levelPack.levels.Length - 1);
            var lvl = levelPack.levels[_levelIndex];
            _moves = lvl.moves;
            _goals.Clear();
            _collected.Clear();
            foreach (var g in lvl.goals)
            {
                _goals[g.essence] = g.amount;
                _collected[g.essence] = 0;
            }

            board.catalog = catalog;
            board.Build(lvl.boardSize, lvl.colorCount);
            if (input != null) input.board = board;

            hud.SetLevel(_levelIndex + 1);
            hud.SetMoves(_moves);
            hud.SetScore(_score);
            hud.SetGoals(_goals, _collected, catalog);
            if (resultPopup != null) resultPopup.Hide();
        }

        void HandleCleared(EssenceId essence, int count, bool burst)
        {
            if (_levelOver) return;
            _moves = Mathf.Max(0, _moves - 1);
            _score += count * (burst ? 25 : 10);
            if (_collected.ContainsKey(essence))
                _collected[essence] += count;

            hud.SetMoves(_moves);
            hud.SetScore(_score);
            hud.SetGoals(_goals, _collected, catalog);
            if (burst) hud.FlashHint("Bloom Burst!");
            else hud.FlashHint($"Linked {count}");
        }

        void HandleSettled()
        {
            if (_levelOver) return;
            if (GoalsMet())
            {
                _levelOver = true;
                bool last = _levelIndex >= levelPack.levels.Length - 1;
                resultPopup.Show(
                    last ? "Garden complete" : "Meadow cleared",
                    last ? $"Final score {_score}" : $"Level {_levelIndex + 1} done · Score {_score}",
                    last ? "Play again" : "Next level"
                );
            }
            else if (_moves <= 0)
            {
                _levelOver = true;
                resultPopup.Show("Out of moves", "Dusk fell on the meadow. Try a new path.", "Retry");
            }
        }

        bool GoalsMet()
        {
            foreach (var kv in _goals)
            {
                _collected.TryGetValue(kv.Key, out int got);
                if (got < kv.Value) return false;
            }
            return true;
        }

        void HandleContinue()
        {
            if (GoalsMet())
            {
                if (_levelIndex >= levelPack.levels.Length - 1) StartLevel(0);
                else StartLevel(_levelIndex + 1);
            }
            else
            {
                StartLevel(_levelIndex);
            }
        }
    }
}
