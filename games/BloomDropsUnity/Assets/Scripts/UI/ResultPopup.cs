using System;
using UnityEngine;
using UnityEngine.UI;

namespace BloomDrops.UI
{
    public class ResultPopup : MonoBehaviour
    {
        public GameObject root;
        public Text titleText;
        public Text bodyText;
        public Text buttonLabel;
        public Button continueButton;

        public event Action OnContinue;

        void Awake()
        {
            if (continueButton != null)
                continueButton.onClick.AddListener(() => OnContinue?.Invoke());
            Hide();
        }

        public void Show(string title, string body, string button)
        {
            if (root) root.SetActive(true);
            if (titleText) titleText.text = title;
            if (bodyText) bodyText.text = body;
            if (buttonLabel) buttonLabel.text = button;
        }

        public void Hide()
        {
            if (root) root.SetActive(false);
        }
    }
}
