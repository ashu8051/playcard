using BloomDrops.Data;
using UnityEngine;

namespace BloomDrops.Core
{
    /// <summary>
    /// Pointer / mouse / touch input for linking drops.
    /// </summary>
    public class BoardInput : MonoBehaviour
    {
        public BoardController board;

        bool _dragging;

        void Update()
        {
            if (board == null || board.InputLocked) return;

            if (Input.GetMouseButtonDown(0))
            {
                var drop = board.HitDrop(Input.mousePosition);
                if (drop != null)
                {
                    _dragging = true;
                    board.BeginPath(drop);
                }
            }
            else if (_dragging && Input.GetMouseButton(0))
            {
                var drop = board.HitDrop(Input.mousePosition);
                if (drop != null) board.ExtendPath(drop);
            }
            else if (_dragging && Input.GetMouseButtonUp(0))
            {
                _dragging = false;
                board.EndPath();
            }
        }
    }
}
