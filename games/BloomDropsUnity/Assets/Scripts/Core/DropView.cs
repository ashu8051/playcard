using System.Collections.Generic;
using BloomDrops.Data;
using UnityEngine;

namespace BloomDrops.Core
{
    /// <summary>
    /// One botanical drop on the board (Two Dots-style cell).
    /// </summary>
    public class DropView : MonoBehaviour
    {
        public EssenceId Essence { get; private set; }
        public int Row { get; private set; }
        public int Col { get; private set; }

        [SerializeField] SpriteRenderer spriteRenderer;
        [SerializeField] float popScale = 1.12f;

        Vector3 _baseScale;
        bool _selected;

        void Awake()
        {
            if (spriteRenderer == null)
                spriteRenderer = GetComponent<SpriteRenderer>();
            _baseScale = transform.localScale;
        }

        public void Setup(EssenceId essence, Sprite sprite, Color tint, int row, int col)
        {
            Essence = essence;
            Row = row;
            Col = col;
            if (spriteRenderer != null)
            {
                spriteRenderer.sprite = sprite;
                spriteRenderer.color = tint;
            }
            name = $"Drop_{row}_{col}_{essence}";
            SetSelected(false);
        }

        public void SetGridPos(int row, int col)
        {
            Row = row;
            Col = col;
            name = $"Drop_{row}_{col}_{Essence}";
        }

        public void SetSelected(bool selected)
        {
            _selected = selected;
            transform.localScale = _baseScale * (selected ? popScale : 1f);
        }

        public void SetWorldPosition(Vector3 pos, bool instant)
        {
            if (instant) transform.position = pos;
            else
            {
                // Lightweight smooth settle without coroutines on every call
                transform.position = Vector3.Lerp(transform.position, pos, 0.35f);
            }
        }
    }
}
