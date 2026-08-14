using UnityEngine;

namespace BloomDrops.Core
{
    /// <summary>
    /// Keeps orthographic camera framed for portrait boards.
    /// </summary>
    public class CameraFit : MonoBehaviour
    {
        public Camera target;
        public float padding = 1.15f;
        public BoardController board;

        void LateUpdate()
        {
            if (target == null) target = Camera.main;
            if (target == null || board == null || board.Size <= 0) return;
            float half = board.Size * board.cellSize * 0.5f * padding;
            target.orthographic = true;
            target.orthographicSize = Mathf.Max(5.5f, half + 1.8f);
        }
    }
}
