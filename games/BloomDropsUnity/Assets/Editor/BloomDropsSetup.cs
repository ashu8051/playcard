#if UNITY_EDITOR
using System.IO;
using BloomDrops.Core;
using BloomDrops.Data;
using BloomDrops.UI;
using UnityEditor;
using UnityEditor.SceneManagement;
using UnityEngine;
using UnityEngine.UI;

namespace BloomDrops.EditorTools
{
    /// <summary>
    /// One-click project bootstrap: sprites, ScriptableObjects, prefabs, playable scene.
    /// Menu: Bloom Drops > Setup Project And Scene
    /// </summary>
    public static class BloomDropsSetup
    {
        const string Root = "Assets";
        const string ArtDrops = "Assets/Art/Drops";
        const string ArtUI = "Assets/Art/UI";
        const string DataPath = "Assets/Resources";
        const string PrefabPath = "Assets/Prefabs";
        const string ScenePath = "Assets/Scenes/BloomDrops.unity";

        [MenuItem("Bloom Drops/Setup Project And Scene")]
        public static void SetupEverything()
        {
            EnsureFolders();
            ConfigureSprites();
            var catalog = CreateCatalog();
            var levels = CreateLevelPack();
            var dropPrefab = CreateDropPrefab(catalog);
            var goalChip = CreateGoalChipPrefab();
            CreatePlayScene(catalog, levels, dropPrefab, goalChip);
            AssetDatabase.SaveAssets();
            AssetDatabase.Refresh();
            EditorSceneManager.OpenScene(ScenePath);
            Debug.Log("Bloom Drops setup complete. Press Play.");
        }

        static Font UiFont()
        {
            var font = Resources.GetBuiltinResource<Font>("LegacyRuntime.ttf");
            if (font == null) font = Resources.GetBuiltinResource<Font>("Arial.ttf");
            return font;
        }

        static void EnsureFolders()
        {
            Directory.CreateDirectory("Assets/Resources");
            Directory.CreateDirectory("Assets/Prefabs");
            Directory.CreateDirectory("Assets/Scenes");
            AssetDatabase.Refresh();
        }

        static void ConfigureSprites()
        {
            string[] files =
            {
                ArtDrops + "/drop-coral.png",
                ArtDrops + "/drop-mint.png",
                ArtDrops + "/drop-gold.png",
                ArtDrops + "/drop-sky.png",
                ArtDrops + "/drop-blossom.png",
                ArtUI + "/bloom-drops-icon.png",
                ArtUI + "/bloom-drops-bg.png",
            };

            foreach (var path in files)
            {
                var importer = AssetImporter.GetAtPath(path) as TextureImporter;
                if (importer == null) continue;
                importer.textureType = TextureImporterType.Sprite;
                importer.spriteImportMode = SpriteImportMode.Single;
                importer.mipmapEnabled = false;
                importer.filterMode = FilterMode.Bilinear;
                importer.SaveAndReimport();
            }
        }

        static Sprite LoadSprite(string path)
        {
            return AssetDatabase.LoadAssetAtPath<Sprite>(path);
        }

        static EssenceCatalog CreateCatalog()
        {
            var path = DataPath + "/EssenceCatalog.asset";
            var catalog = AssetDatabase.LoadAssetAtPath<EssenceCatalog>(path);
            if (catalog == null)
            {
                catalog = ScriptableObject.CreateInstance<EssenceCatalog>();
                AssetDatabase.CreateAsset(catalog, path);
            }

            catalog.essences = new[]
            {
                Def(EssenceId.Coral, "Coral Rose", new Color(1f, 0.42f, 0.42f), ArtDrops + "/drop-coral.png"),
                Def(EssenceId.Mint, "Mint Dew", new Color(0.37f, 0.92f, 0.83f), ArtDrops + "/drop-mint.png"),
                Def(EssenceId.Gold, "Sun Pollen", new Color(0.98f, 0.75f, 0.14f), ArtDrops + "/drop-gold.png"),
                Def(EssenceId.Sky, "Sky Drop", new Color(0.38f, 0.65f, 0.98f), ArtDrops + "/drop-sky.png"),
                Def(EssenceId.Blossom, "Night Blossom", new Color(0.98f, 0.66f, 0.83f), ArtDrops + "/drop-blossom.png"),
            };
            EditorUtility.SetDirty(catalog);
            return catalog;
        }

        static EssenceDef Def(EssenceId id, string name, Color color, string spritePath)
        {
            return new EssenceDef
            {
                id = id,
                displayName = name,
                color = color,
                sprite = LoadSprite(spritePath)
            };
        }

        static LevelPack CreateLevelPack()
        {
            var path = DataPath + "/LevelPack.asset";
            var pack = AssetDatabase.LoadAssetAtPath<LevelPack>(path);
            if (pack == null)
            {
                pack = ScriptableObject.CreateInstance<LevelPack>();
                AssetDatabase.CreateAsset(pack, path);
            }

            pack.levels = new[]
            {
                new LevelDef
                {
                    boardSize = 6, moves = 22, colorCount = 4,
                    goals = new[]
                    {
                        new GoalDef { essence = EssenceId.Coral, amount = 18 },
                        new GoalDef { essence = EssenceId.Mint, amount = 18 },
                        new GoalDef { essence = EssenceId.Gold, amount = 12 },
                    }
                },
                new LevelDef
                {
                    boardSize = 6, moves = 20, colorCount = 4,
                    goals = new[]
                    {
                        new GoalDef { essence = EssenceId.Coral, amount = 20 },
                        new GoalDef { essence = EssenceId.Mint, amount = 16 },
                        new GoalDef { essence = EssenceId.Sky, amount = 16 },
                    }
                },
                new LevelDef
                {
                    boardSize = 7, moves = 24, colorCount = 5,
                    goals = new[]
                    {
                        new GoalDef { essence = EssenceId.Gold, amount = 22 },
                        new GoalDef { essence = EssenceId.Blossom, amount = 18 },
                        new GoalDef { essence = EssenceId.Mint, amount = 18 },
                    }
                },
                new LevelDef
                {
                    boardSize = 7, moves = 22, colorCount = 5,
                    goals = new[]
                    {
                        new GoalDef { essence = EssenceId.Coral, amount = 20 },
                        new GoalDef { essence = EssenceId.Sky, amount = 20 },
                        new GoalDef { essence = EssenceId.Gold, amount = 16 },
                        new GoalDef { essence = EssenceId.Mint, amount = 12 },
                    }
                },
                new LevelDef
                {
                    boardSize = 8, moves = 26, colorCount = 5,
                    goals = new[]
                    {
                        new GoalDef { essence = EssenceId.Coral, amount = 24 },
                        new GoalDef { essence = EssenceId.Mint, amount = 24 },
                        new GoalDef { essence = EssenceId.Gold, amount = 20 },
                        new GoalDef { essence = EssenceId.Blossom, amount = 16 },
                    }
                },
            };
            EditorUtility.SetDirty(pack);
            return pack;
        }

        static DropView CreateDropPrefab(EssenceCatalog catalog)
        {
            var path = PrefabPath + "/Drop.prefab";
            var go = new GameObject("Drop", typeof(SpriteRenderer), typeof(DropView));
            var sr = go.GetComponent<SpriteRenderer>();
            sr.sprite = catalog.essences[0].sprite;
            sr.sortingOrder = 2;
            go.transform.localScale = Vector3.one * 0.85f;
            var view = go.GetComponent<DropView>();
            var so = new SerializedObject(view);
            so.FindProperty("spriteRenderer").objectReferenceValue = sr;
            so.ApplyModifiedPropertiesWithoutUndo();

            var prefab = PrefabUtility.SaveAsPrefabAsset(go, path);
            Object.DestroyImmediate(go);
            return prefab.GetComponent<DropView>();
        }

        static GoalChip CreateGoalChipPrefab()
        {
            var path = PrefabPath + "/GoalChip.prefab";
            var root = new GameObject("GoalChip", typeof(RectTransform), typeof(HorizontalLayoutGroup), typeof(GoalChip));
            var hl = root.GetComponent<HorizontalLayoutGroup>();
            hl.childAlignment = TextAnchor.MiddleCenter;
            hl.spacing = 6;
            hl.padding = new RectOffset(6, 10, 4, 4);
            hl.childForceExpandWidth = false;
            hl.childForceExpandHeight = false;

            var imgGo = new GameObject("Icon", typeof(RectTransform), typeof(Image));
            imgGo.transform.SetParent(root.transform, false);
            var img = imgGo.GetComponent<Image>();
            var imgRt = imgGo.GetComponent<RectTransform>();
            imgRt.sizeDelta = new Vector2(28, 28);

            var textGo = new GameObject("Label", typeof(RectTransform), typeof(Text));
            textGo.transform.SetParent(root.transform, false);
            var text = textGo.GetComponent<Text>();
            text.font = UiFont();
            text.fontSize = 18;
            text.color = Color.white;
            text.alignment = TextAnchor.MiddleLeft;
            text.text = "0/10";
            var textRt = textGo.GetComponent<RectTransform>();
            textRt.sizeDelta = new Vector2(64, 28);

            var chip = root.GetComponent<GoalChip>();
            var so = new SerializedObject(chip);
            so.FindProperty("icon").objectReferenceValue = img;
            so.FindProperty("label").objectReferenceValue = text;
            so.ApplyModifiedPropertiesWithoutUndo();

            var prefab = PrefabUtility.SaveAsPrefabAsset(root, path);
            Object.DestroyImmediate(root);
            return prefab.GetComponent<GoalChip>();
        }

        static void CreatePlayScene(EssenceCatalog catalog, LevelPack levels, DropView dropPrefab, GoalChip goalChip)
        {
            var scene = EditorSceneManager.NewScene(NewSceneSetup.DefaultGameObjects, NewSceneMode.Single);
            var cam = Camera.main;
            cam.orthographic = true;
            cam.orthographicSize = 6.2f;
            cam.backgroundColor = new Color(0.027f, 0.102f, 0.114f);
            cam.transform.position = new Vector3(0, 0.4f, -10);

            // Background
            var bg = new GameObject("Background", typeof(SpriteRenderer));
            var bgSr = bg.GetComponent<SpriteRenderer>();
            bgSr.sprite = LoadSprite(ArtUI + "/bloom-drops-bg.png");
            bgSr.sortingOrder = -20;
            bg.transform.localScale = new Vector3(1.35f, 1.35f, 1f);
            bg.transform.position = new Vector3(0, 0, 1);

            // Board
            var boardGo = new GameObject("Board");
            var board = boardGo.AddComponent<BoardController>();
            board.catalog = catalog;
            board.dropPrefab = dropPrefab;
            board.boardRoot = boardGo.transform;
            board.cellSize = 1.05f;

            var lineGo = new GameObject("LinkLine");
            lineGo.transform.SetParent(boardGo.transform, false);
            var lr = lineGo.AddComponent<LineRenderer>();
            lr.material = new Material(Shader.Find("Sprites/Default"));
            lr.numCapVertices = 4;
            lr.sortingOrder = 10;
            board.linkLine = lr;

            var input = boardGo.AddComponent<BoardInput>();
            input.board = board;

            // UI Canvas
            var canvasGo = new GameObject("Canvas", typeof(Canvas), typeof(CanvasScaler), typeof(GraphicRaycaster));
            var canvas = canvasGo.GetComponent<Canvas>();
            canvas.renderMode = RenderMode.ScreenSpaceOverlay;
            var scaler = canvasGo.GetComponent<CanvasScaler>();
            scaler.uiScaleMode = CanvasScaler.ScaleMode.ScaleWithScreenSize;
            scaler.referenceResolution = new Vector2(1080, 1920);
            scaler.matchWidthOrHeight = 0.5f;

            if (Object.FindObjectOfType<UnityEngine.EventSystems.EventSystem>() == null)
            {
                new GameObject("EventSystem", typeof(UnityEngine.EventSystems.EventSystem), typeof(UnityEngine.EventSystems.StandaloneInputModule));
            }

            // Top HUD
            var hudGo = CreatePanel(canvasGo.transform, "HUD", new Vector2(0.5f, 1f), new Vector2(0.5f, 1f), new Vector2(0, -120), new Vector2(1000, 220));
            var brand = CreateText(hudGo.transform, "Title", "Bloom Drops", 54, TextAnchor.UpperCenter, new Vector2(0, 70), new Vector2(800, 70));
            brand.fontStyle = FontStyle.Bold;

            var stats = CreatePanel(hudGo.transform, "Stats", new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), new Vector2(0, -10), new Vector2(900, 90));
            var h = stats.gameObject.AddComponent<HorizontalLayoutGroup>();
            h.childAlignment = TextAnchor.MiddleCenter;
            h.spacing = 24;
            h.childForceExpandHeight = true;
            h.childForceExpandWidth = true;

            var levelText = CreateStat(stats, "Level", "1");
            var movesText = CreateStat(stats, "Moves", "22");
            var scoreText = CreateStat(stats, "Score", "0");

            var goalsRoot = CreatePanel(canvasGo.transform, "Goals", new Vector2(0.5f, 1f), new Vector2(0.5f, 1f), new Vector2(0, -270), new Vector2(900, 60));
            var goalsLayout = goalsRoot.gameObject.AddComponent<HorizontalLayoutGroup>();
            goalsLayout.childAlignment = TextAnchor.MiddleCenter;
            goalsLayout.spacing = 12;
            goalsLayout.childForceExpandWidth = false;

            var hint = CreateText(canvasGo.transform, "Hint", "Drag matching drops. Close a loop for a Bloom Burst.", 28, TextAnchor.LowerCenter, new Vector2(0, 160), new Vector2(900, 50));

            var restartBtn = CreateButton(canvasGo.transform, "Restart", "Restart", new Vector2(0, 80), new Vector2(220, 70));

            // Result popup
            var popupRoot = CreatePanel(canvasGo.transform, "ResultPopup", new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), Vector2.zero, new Vector2(720, 420));
            var popupImg = popupRoot.gameObject.AddComponent<Image>();
            popupImg.color = new Color(0.07f, 0.2f, 0.22f, 0.96f);
            var popupTitle = CreateText(popupRoot.transform, "PopupTitle", "Meadow cleared", 44, TextAnchor.MiddleCenter, new Vector2(0, 100), new Vector2(640, 60));
            var popupBody = CreateText(popupRoot.transform, "PopupBody", "Nice harvest.", 28, TextAnchor.MiddleCenter, new Vector2(0, 20), new Vector2(640, 80));
            var popupBtn = CreateButton(popupRoot.transform, "Continue", "Continue", new Vector2(0, -110), new Vector2(260, 70));
            popupRoot.gameObject.SetActive(false);

            var result = popupRoot.gameObject.AddComponent<ResultPopup>();
            var rso = new SerializedObject(result);
            rso.FindProperty("root").objectReferenceValue = popupRoot.gameObject;
            rso.FindProperty("titleText").objectReferenceValue = popupTitle;
            rso.FindProperty("bodyText").objectReferenceValue = popupBody;
            rso.FindProperty("buttonLabel").objectReferenceValue = popupBtn.GetComponentInChildren<Text>();
            rso.FindProperty("continueButton").objectReferenceValue = popupBtn.GetComponent<Button>();
            rso.ApplyModifiedPropertiesWithoutUndo();

            var hud = canvasGo.AddComponent<HudController>();
            var hso = new SerializedObject(hud);
            hso.FindProperty("levelText").objectReferenceValue = levelText;
            hso.FindProperty("movesText").objectReferenceValue = movesText;
            hso.FindProperty("scoreText").objectReferenceValue = scoreText;
            hso.FindProperty("hintText").objectReferenceValue = hint;
            hso.FindProperty("goalsRoot").objectReferenceValue = goalsRoot;
            hso.FindProperty("goalChipPrefab").objectReferenceValue = goalChip;
            hso.ApplyModifiedPropertiesWithoutUndo();

            var gmGo = new GameObject("GameManager");
            var gm = gmGo.AddComponent<GameManager>();
            var gso = new SerializedObject(gm);
            gso.FindProperty("catalog").objectReferenceValue = catalog;
            gso.FindProperty("levelPack").objectReferenceValue = levels;
            gso.FindProperty("board").objectReferenceValue = board;
            gso.FindProperty("input").objectReferenceValue = input;
            gso.FindProperty("hud").objectReferenceValue = hud;
            gso.FindProperty("resultPopup").objectReferenceValue = result;
            gso.ApplyModifiedPropertiesWithoutUndo();

            var rb = restartBtn.gameObject.AddComponent<RestartButton>();
            var rbso = new SerializedObject(rb);
            rbso.FindProperty("gameManager").objectReferenceValue = gm;
            rbso.FindProperty("button").objectReferenceValue = restartBtn.GetComponent<Button>();
            rbso.ApplyModifiedPropertiesWithoutUndo();

            EditorSceneManager.SaveScene(scene, ScenePath);

            var buildScenes = new[] { new EditorBuildSettingsScene(ScenePath, true) };
            EditorBuildSettings.scenes = buildScenes;
        }

        static RectTransform CreatePanel(Transform parent, string name, Vector2 anchorMin, Vector2 anchorMax, Vector2 anchoredPos, Vector2 size)
        {
            var go = new GameObject(name, typeof(RectTransform));
            go.transform.SetParent(parent, false);
            var rt = go.GetComponent<RectTransform>();
            rt.anchorMin = anchorMin;
            rt.anchorMax = anchorMax;
            rt.pivot = new Vector2(0.5f, 0.5f);
            rt.anchoredPosition = anchoredPos;
            rt.sizeDelta = size;
            return rt;
        }

        static Text CreateText(Transform parent, string name, string content, int size, TextAnchor align, Vector2 pos, Vector2 dim)
        {
            var go = new GameObject(name, typeof(RectTransform), typeof(Text));
            go.transform.SetParent(parent, false);
            var rt = go.GetComponent<RectTransform>();
            rt.anchorMin = rt.anchorMax = new Vector2(0.5f, 0.5f);
            rt.anchoredPosition = pos;
            rt.sizeDelta = dim;
            var t = go.GetComponent<Text>();
            t.text = content;
            t.font = UiFont();
            t.fontSize = size;
            t.alignment = align;
            t.color = Color.white;
            t.horizontalOverflow = HorizontalWrapMode.Wrap;
            t.verticalOverflow = VerticalWrapMode.Overflow;
            return t;
        }

        static Text CreateStat(Transform parent, string label, string value)
        {
            var box = new GameObject(label, typeof(RectTransform), typeof(Image), typeof(VerticalLayoutGroup));
            box.transform.SetParent(parent, false);
            box.GetComponent<Image>().color = new Color(0.05f, 0.16f, 0.18f, 0.72f);
            var v = box.GetComponent<VerticalLayoutGroup>();
            v.childAlignment = TextAnchor.MiddleCenter;
            v.padding = new RectOffset(8, 8, 8, 8);

            CreateText(box.transform, "L", label.ToUpperInvariant(), 16, TextAnchor.MiddleCenter, Vector2.zero, new Vector2(120, 24));
            return CreateText(box.transform, "V", value, 34, TextAnchor.MiddleCenter, Vector2.zero, new Vector2(120, 40));
        }

        static GameObject CreateButton(Transform parent, string name, string label, Vector2 pos, Vector2 size)
        {
            var go = new GameObject(name, typeof(RectTransform), typeof(Image), typeof(Button));
            go.transform.SetParent(parent, false);
            var rt = go.GetComponent<RectTransform>();
            rt.anchorMin = rt.anchorMax = new Vector2(0.5f, 0f);
            rt.pivot = new Vector2(0.5f, 0.5f);
            rt.anchoredPosition = pos;
            rt.sizeDelta = size;
            go.GetComponent<Image>().color = new Color(0.98f, 0.75f, 0.14f, 1f);
            var text = CreateText(go.transform, "Text", label, 28, TextAnchor.MiddleCenter, Vector2.zero, size);
            text.color = new Color(0.08f, 0.12f, 0.1f);
            text.fontStyle = FontStyle.Bold;
            var trt = text.GetComponent<RectTransform>();
            trt.anchorMin = Vector2.zero;
            trt.anchorMax = Vector2.one;
            trt.offsetMin = Vector2.zero;
            trt.offsetMax = Vector2.zero;
            return go;
        }
    }
}
#endif
