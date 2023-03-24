# Server endpoints of the Android Google Keep API

## Auth
*See [here](https://github.com/rukins/gpsoauth-java/blob/master/server-endpoints.md)*

Each request to API should have the `Authorization` header with access token:
```
Authorization: OAuth ya29.
```


## Changes
All manipulations with nodes and labels are accessible through this endpoint:

```http request
POST https://notes-pa.googleapis.com/notes/v1/changes
```

*Body*:
```json
{
    "clientTimestamp": "2023-03-24T22:28:40.070Z",
    "userInfo": {
        "labels": [
            {
                "name": "example",
                "mainId": "eb5132b1310b49.3a0107eabfbd6d8e3",
                "timestamps": {
                    "created": "2023-03-24T22:28:40.070Z",
                    "updated": "2023-03-24T22:28:40.070Z"
                }
            }
        ]
    },
    "nodes": [
        {
            "id": "148c94qwewef48.08174ew3B452761",
            "type": "NOTE",
            "title": "some note"
        },
        {
            "id": "14851c9jek48.08ewec752761",
            "parentId": "148c94qwewef48.08174ew3B452761",
            "type": "LIST_ITEM",
            "text": "some note text"
        }
    ],
    "requestHeader": {
        "capabilities": [
        {
            "type": "TR"
        },
        {
            "type": "EC"
        },
        {
            "type": "SH"
        },
        {
            "type": "RB"
        },
        {
            "type": "LB"
        },
        {
            "type": "DR"
        },
        {
            "type": "AN"
        },
        {
            "type": "PI"
        },
        {
            "type": "EX"
        },
        {
            "type": "CO"
        },
        {
            "type": "MI"
        },
        {
            "type": "SNB"
        },
        {
            "type": "IN"
        },
        {
            "type": "PS"
        },
        {
            "type": "NC"
        }
        ],
        "clientLocale": "en_US",
        "clientPlatform": "ANDROID",
        "clientSessionId": "ad7ab55d-005c-48br-9ea3-40b2da07d380",
        "clientVersion": {
            "build": 62,
            "major": 5,
            "minor": 23,
            "revision": "0"
        },
        "noteSupportedModelFeatures": ""
    },
    "targetVersion": "AD96wSTswl8vPUAoMelj1i9wXLkPIh48YyJlsnyRcy5EBmw6U21vLdE="
}
```

*Returns:*
```json
{
    "kind": "notes#downSync",
    "fromVersion": "AD96wSTswl8vPUAoMelj1i9wXLkPIh48YyJlsnyRcy5EBmw6U21vLdE=",
    "toVersion": "AILBLhnk56c32MutNyhHIQ3kWMOnDkyQIee1AGwmBltk5O7gLafeTzKOfgmtveuuZQ9DC7kf610ynqE4BWduIn49riym5zd0RcVTunL7pz8HkU0zYo8OWL7c4bQGlAsB",
    "userInfo": {
        "timestamps": {
            "kind": "notes#timestamps",
            "created": "2023-02-25T16:08:50.325Z"
        },
        "settings": {
            "singleSettings": [
                {
                    "type": "GLOBAL_CHECKED_LIST_ITEMS_POLICY",
                    "applicablePlatforms": [
                        "ANDROID",
                        "WEB",
                        "CRX",
                        "IOS",
                        "CRX_BA"
                    ],
                    "globalCheckedListItemsPolicyValue": "GRAVEYARD"
                },
                {
                    "type": "GLOBAL_NEW_LIST_ITEM_PLACEMENT",
                    "applicablePlatforms": [
                        "ANDROID",
                        "WEB",
                        "CRX",
                        "IOS",
                        "CRX_BA"
                    ],
                    "globalNewListItemPlacementValue": "TOP"
                },
                {
                    "type": "LAYOUT_STYLE",
                    "layoutStyleValue": "GRID",
                    "applicablePlatforms": [
                        "ANDROID",
                        "WEB",
                        "CRX",
                        "IOS",
                        "CRX_BA"
                    ]
                },
                {
                    "type": "SHARING_ENABLED",
                    "applicablePlatforms": [
                        "ANDROID",
                        "WEB",
                        "CRX",
                        "IOS",
                        "CRX_BA"
                    ],
                    "sharingEnabledValue": false
                },
                {
                    "type": "WEB_EMBEDS_ENABLED",
                    "applicablePlatforms": [
                        "ANDROID",
                        "WEB",
                        "CRX",
                        "IOS",
                        "CRX_BA"
                    ],
                    "webEmbedsEnabledValue": true
                },
                {
                    "type": "WEB_APP_THEME",
                    "applicablePlatforms": [
                        "ANDROID",
                        "WEB",
                        "CRX",
                        "IOS",
                        "CRX_BA"
                    ],
                    "webAppThemeValue": "DARK"
                }
            ]
        },
        "contextualCoachmarksAcked": [
            "pinning_cc",
            "web_dark_theme_cc"
        ],
        "labels": [
            {
                "name": "example",
                "mainId": "eb5132b1310b49.3a0107eabfbd6d8e3",
                "timestamps": {
                    "created": "2023-03-24T17:34:02.080Z",
                    "updated": "2023-03-24T17:34:02.080Z"
                }
            }
        ]
    },
    "nodes": [
        {
            "kind": "notes#node",
            "id": "148c94qwewef48.08174ew3B452761",
            "serverId": "1rw6vCWlGBMXyIovnyo9owHZXffrXmr_TvIhn40SiLtp3RraEzaCtXnv0tXa5OF5o9dJsze8",
            "parentId": "root",
            "type": "NOTE",
            "timestamps": {
                "kind": "notes#timestamps",
                "created": "2023-03-24T17:34:02.441Z",
                "updated": "2023-03-24T17:34:02.441Z",
                "userEdited": "2023-03-24T17:34:02.441Z"
            },
            "title": "some note",
            "nodeSettings": {
                "newListItemPlacement": "BOTTOM",
                "checkedListItemsPolicy": "GRAVEYARD",
                "graveyardState": "EXPANDED"
            },
            "isArchived": false,
            "sortValue": "0",
            "annotationsGroup": {
                "kind": "notes#annotationsGroup"
            },
            "lastModifierEmail": "rukins280@gmail.com",
            "moved": "1",
            "xplatModel": false
        },
        {
            "kind": "notes#node",
            "id": "14851c9jek48.08ewec752761",
            "serverId": "1zlnyindhDOxFqAikEDZZOgG4YfEvzogGh5DC6d2Gg-MppH2gdYv4qliTL5b17Wzf",
            "parentId": "148c94qwewef48.08174ew3B452761",
            "parentServerId": "1rw6vCWlGBMXyIovnyo9owHZXffrXmr_TvIhn40SiLtp3RraEzaCtXnv0tXa5OF5o9dJsze8",
            "type": "LIST_ITEM",
            "timestamps": {
                "kind": "notes#timestamps",
                "created": "2023-03-24T17:34:02.441Z",
                "updated": "2023-03-24T17:34:02.441Z"
            },
            "text": "some note text",
            "checked": false,
            "baseVersion": "1",
            "nodeSettings": {
                "newListItemPlacement": "BOTTOM",
                "checkedListItemsPolicy": "GRAVEYARD",
                "graveyardState": "EXPANDED"
            },
            "annotationsGroup": {
                "kind": "notes#annotationsGroup"
            }
        }
    ],
    "truncated": false,
    "forceFullResync": false,
    "responseHeader": {
        "updateState": "UTD",
        "requestId": ""
    }
}
```

## Uploading image to the server

### First request
```http request
POST https://notes-pa.googleapis.com/notes/v1/changes
```

*Body:*
```json
{
  "clientTimestamp": "2023-03-24T18:44:33.633Z",
  "nodes": [
    {
      "id": "18714efa722.8985a5e833fea45d",
      "parentId": "root",
      "sortValue": "29360128",
      "title": "",
      "type": "NOTE"
    },
    {
      "id": "18714efa780.a58ff8cbeb904dc0",
      "parentId": "18714efa722.8985a5e833fea45d",
      "text": "",
      "type": "LIST_ITEM"
    }
  ],
  "requestHeader": {/* default requestHeader */},
  "targetVersion": ""
}
```

*Returns:*
```json
{
  "kind": "notes#downSync",
  "fromVersion": "",
  "toVersion": "",
  "nodes": [
    {
      "kind": "notes#node",
      "id": "18714efa722.8985a5e833fea45d",
      "serverId": "14KbhERzkGxjzXh7EByQ7KJIRcX59yVVd6c2SCpYgzrr1qGWSFShb87qbOxPzotDvYcbr",
      "parentId": "root",
      "type": "NOTE",
      "timestamps": {
        "kind": "notes#timestamps",
        "created": "2023-03-24T18:44:34.453Z",
        "updated": "2023-03-24T18:44:34.453Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "userEdited": "2023-03-24T18:44:34.453Z",
        "recentSharedChangesSeen": "2023-03-24T18:44:27.704Z"
      },
      "title": "",
      "nodeSettings": {
        "newListItemPlacement": "BOTTOM",
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED"
      },
      "isArchived": false,
      "isPinned": false,
      "color": "DEFAULT",
      "sortValue": "29360128",
      "annotationsGroup": {
        "kind": "notes#annotationsGroup"
      },
      "lastModifierEmail": "",
      "moved": "1",
      "xplatModel": false
    },
    {
      "kind": "notes#node",
      "id": "18714efa780.a58ff8cbeb904dc0",
      "serverId": "13AxZX9vvNlCXmqHotXOnNmmhBG3h0je2QCSAZ6emNdyO3V82BTsFDSlmjMjpS5DD0Mv2",
      "parentId": "18714efa722.8985a5e833fea45d",
      "parentServerId": "14KbhERzkGxjzXh7EByQ7KJIRcX59yVVd6c2SCpYgzrr1qGWSFShb87qbOxPzotDvYcbr",
      "type": "LIST_ITEM",
      "timestamps": {
        "kind": "notes#timestamps",
        "created": "2023-03-24T18:44:34.453Z",
        "updated": "2023-03-24T18:44:34.453Z"
      },
      "text": "",
      "checked": false,
      "baseVersion": "0",
      "nodeSettings": {
        "newListItemPlacement": "BOTTOM",
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED"
      },
      "sortValue": "0",
      "annotationsGroup": {
        "kind": "notes#annotationsGroup"
      }
    }
  ],
  "truncated": false,
  "forceFullResync": false,
  "responseHeader": {
    "updateState": "UTD",
    "requestId": ""
  }
}
```

### Second request
```http request
POST https://notes-pa.googleapis.com/notes/v1/changes
```

*Body:*
```json
{
  "clientTimestamp": "2023-03-24T18:44:47.625Z",
  "nodes": [
    {
      "color": "DEFAULT",
      "id": "18714efa722.8985a5e833fea45d",
      "isArchived": false,
      "isPinned": false,
      "lastModifierEmail": "",
      "nodeSettings": {
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED",
        "newListItemPlacement": "BOTTOM"
      },
      "parentId": "root",
      "serverId": "14KbhERzkGxjzXh7EByQ7KJIRcX59yVVd6c2SCpYgzrr1qGWSFShb87qbOxPzotDvYcbr",
      "shareState": "ACCEPTED",
      "sortValue": "29360128",
      "timestamps": {
        "created": "2023-03-24T18:44:23.063Z",
        "recentSharedChangesSeen": "2023-03-24T18:44:38.893Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "updated": "2023-03-24T18:44:47.491Z",
        "userEdited": "2023-03-24T18:44:47.491Z"
      },
      "title": "",
      "type": "NOTE"
    },
    {
      "blob": {
        "byte_size": 160944,
        "extraction_status": "PROCESSING_REQUESTED",
        "height": 1280,
        "mimetype": "image/jpeg",
        "type": "IMAGE",
        "width": 960
      },
      "id": "18714effb0d.86af610f3c3c318c",
      "parentId": "18714efa722.8985a5e833fea45d",
      "parentServerId": "14KbhERzkGxjzXh7EByQ7KJIRcX59yVVd6c2SCpYgzrr1qGWSFShb87qbOxPzotDvYcbr",
      "timestamps": {
        "created": "2023-03-24T18:44:44.429Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "updated": "2023-03-24T18:44:47.491Z"
      },
      "type": "BLOB"
    }
  ],
  "requestHeader": {/* default requestHeader */},
  "targetVersion": ""
}
```

*Returns:* 
```json
{
  "kind": "notes#downSync",
  "fromVersion": "",
  "toVersion": "",
  "nodes": [
    {
      "kind": "notes#node",
      "id": "18714efa722.8985a5e833fea45d",
      "serverId": "14KbhERzkGxjzXh7EByQ7KJIRcX59yVVd6c2SCpYgzrr1qGWSFShb87qbOxPzotDvYcbr",
      "parentId": "root",
      "type": "NOTE",
      "timestamps": {
        "kind": "notes#timestamps",
        "created": "2023-03-24T18:44:34.453Z",
        "updated": "2023-03-24T18:44:48.257Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "userEdited": "2023-03-24T18:44:48.257Z",
        "recentSharedChangesSeen": "2023-03-24T18:44:38.893Z"
      },
      "title": "",
      "nodeSettings": {
        "newListItemPlacement": "BOTTOM",
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED"
      },
      "isArchived": false,
      "isPinned": false,
      "color": "DEFAULT",
      "sortValue": "29360128",
      "shareState": "ACCEPTED",
      "annotationsGroup": {
        "kind": "notes#annotationsGroup"
      },
      "lastModifierEmail": "",
      "moved": "1",
      "xplatModel": false
    },
    {
      "kind": "notes#node",
      "id": "18714effb0d.86af610f3c3c318c",
      "serverId": "1-pf2iuWT4ek4cF_k9nk-kdoo5am-kBobCPvPSX0QhWfEF4KEiaQfgZwvfYxjV7xmw1MY",
      "parentId": "18714efa722.8985a5e833fea45d",
      "parentServerId": "14KbhERzkGxjzXh7EByQ7KJIRcX59yVVd6c2SCpYgzrr1qGWSFShb87qbOxPzotDvYcbr",
      "type": "BLOB",
      "timestamps": {
        "kind": "notes#timestamps",
        "created": "2023-03-24T18:44:48.257Z",
        "updated": "2023-03-24T18:44:48.257Z"
      },
      "blob": {
        "kind": "notes#blob",
        "type": "IMAGE",
        "mimetype": "image/jpeg"
      },
      "nodeSettings": {
        "newListItemPlacement": "BOTTOM",
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED"
      },
      "annotationsGroup": {
        "kind": "notes#annotationsGroup"
      }
    }
  ],
  "truncated": false,
  "forceFullResync": false,
  "responseHeader": {
    "updateState": "UTD",
    "requestId": ""
  }
}
```

### Third request

```http request
POST https://notes-pa.googleapis.com/upload/notes/v1/media/{serverId-of-blob}
```

*with parameters:*

- `noteId` - *serverId of the note a blob corresponds to*
- `uploadType` - **resumable**

*Body:*
```json
{}
```

*Returns:* **empty body**
and `Location` header where to upload the image

### Fourth request
*see uri in the `Location` header of previous request*
```http request
PUT https://notes-pa.googleapis.com/upload/notes/v1/media/
```

*Body:* **image in the `HEX` format**

*Returns:* 
```json
{
    "kind": "notes#blob",
    "type": "IMAGE",
    "mimetype": "image/jpeg",
    "byte_size": 160944,
    "width": 960,
    "height": 1280,
    "is_uploaded": true
}
```

### Fifth request
```http request
POST https://notes-pa.googleapis.com/notes/v1/changes
```

*Body:*
```json
{
  "clientTimestamp": "2023-03-24T19:05:29.301Z",
  "nodes": [
    {
      "color": "DEFAULT",
      "id": "18715028654.81099fd6322b1647",
      "isArchived": false,
      "isPinned": false,
      "lastModifierEmail": "",
      "nodeSettings": {
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED",
        "newListItemPlacement": "BOTTOM"
      },
      "parentId": "root",
      "serverId": "1UMnOFsX170PTbQjZ4ZepvlX6riv0ece62YhzT7YBQR5hHI4OaksLeWC8s3C82FoUpiEa",
      "shareState": "ACCEPTED",
      "sortValue": "30408704",
      "timestamps": {
        "created": "2023-03-24T19:04:59.843Z",
        "recentSharedChangesSeen": "2023-03-24T19:05:23.541Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "updated": "2023-03-24T19:05:29.028Z",
        "userEdited": "2023-03-24T19:05:29.028Z"
      },
      "title": "",
      "type": "NOTE"
    },
    {
      "blob": {
        "byte_size": 200714,
        "extraction_status": "DO_NOT_PROCESS",
        "height": 1280,
        "mimetype": "image/jpeg",
        "type": "IMAGE",
        "width": 960
      },
      "id": "18715029e85.bfae5a2429011ec7",
      "parentId": "18715028654.81099fd6322b1647",
      "parentServerId": "1UMnOFsX170PTbQjZ4ZepvlX6riv0ece62YhzT7YBQR5hHI4OaksLeWC8s3C82FoUpiEa",
      "serverId": "1UvkoFXb-ZIC46sksg0t7luvUqeU-TygyzRt7nvIOhaQ2PKHbzz9NqE3GPO6XyNSXPdCA",
      "timestamps": {
        "created": "2023-03-24T19:05:05.925Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "updated": "2023-03-24T19:05:29.028Z"
      },
      "type": "BLOB"
    }
  ],
  "requestHeader": {/* default requestHeader */},
  "targetVersion": ""
}
```

*Returns:* 
```json
{
  "kind": "notes#downSync",
  "fromVersion": "",
  "toVersion": "",
  "nodes": [
    {
      "kind": "notes#node",
      "id": "18715028654.81099fd6322b1647",
      "serverId": "1UMnOFsX170PTbQjZ4ZepvlX6riv0ece62YhzT7YBQR5hHI4OaksLeWC8s3C82FoUpiEa",
      "parentId": "root",
      "type": "NOTE",
      "timestamps": {
        "kind": "notes#timestamps",
        "created": "2023-03-24T19:05:08.178Z",
        "updated": "2023-03-24T19:05:31.166Z",
        "trashed": "1970-01-01T00:00:00.000Z",
        "userEdited": "2023-03-24T19:05:31.166Z",
        "recentSharedChangesSeen": "2023-03-24T19:05:23.541Z"
      },
      "title": "",
      "nodeSettings": {
        "newListItemPlacement": "BOTTOM",
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED"
      },
      "isArchived": false,
      "isPinned": false,
      "color": "DEFAULT",
      "sortValue": "30408704",
      "shareState": "ACCEPTED",
      "annotationsGroup": {
        "kind": "notes#annotationsGroup"
      },
      "lastModifierEmail": "",
      "moved": "1",
      "xplatModel": false
    },
    {
      "kind": "notes#node",
      "id": "18715029e85.bfae5a2429011ec7",
      "serverId": "1UvkoFXb-ZIC46sksg0t7luvUqeU-TygyzRt7nvIOhaQ2PKHbzz9NqE3GPO6XyNSXPdCA",
      "parentId": "18715028654.81099fd6322b1647",
      "parentServerId": "1UMnOFsX170PTbQjZ4ZepvlX6riv0ece62YhzT7YBQR5hHI4OaksLeWC8s3C82FoUpiEa",
      "type": "BLOB",
      "timestamps": {
        "kind": "notes#timestamps",
        "created": "2023-03-24T19:05:26.897Z",
        "updated": "2023-03-24T19:05:31.166Z"
      },
      "blob": {
        "kind": "notes#blob",
        "type": "IMAGE",
        "mimetype": "image/jpeg",
        "byte_size": 200714,
        "width": 960,
        "height": 1280,
        "extraction_status": "FAILED",
        "is_uploaded": true
      },
      "nodeSettings": {
        "newListItemPlacement": "BOTTOM",
        "checkedListItemsPolicy": "GRAVEYARD",
        "graveyardState": "EXPANDED"
      },
      "annotationsGroup": {
        "kind": "notes#annotationsGroup"
      }
    }
  ],
  "truncated": false,
  "forceFullResync": false,
  "responseHeader": {
    "updateState": "UTD",
    "requestId": ""
  }
}
```


## Pulling image from the server

### First request
```http request
POST https://notes-pa.googleapis.com/notes/v1/changes
```

*Body:*
```json
{
    "clientTimestamp": "2023-03-12T10:30:51.263Z",
    "nodes": [],
    "requestHeader": {/* default requestHeader */},
    "targetVersion": ""
  }
```

*Returns:* 
```json
{
    "kind": "notes#downSync",
    "fromVersion": "AILBLhnXdL4uX9yy51KAVdtpSM749ko1nfOEIJCOpLOtAi3eROkjhOOyeJnNqvA6qhf3rWgGVGxzDchIy85WL0GgsI18G9EnMfgGVk9FES8Ue6uFdfeg6/vqbfFl2w==",
    "toVersion": "AILBLhksRMMth1KwypSg5etwbyb5LmQGpEwfcwgwnb+rRXpzJ9Z43YcjiBOuc167CcyxvgMw8owaRDFJ0OkSLisuRSW6P0OVS2e/DjYBWUgXtyw1j1d3lIu4bRkX6w==",
    "nodes": [
      {
        "kind": "notes#node",
        "id": "186d5611e9d.aa4b2bf13f1502bf",
        "serverId": "1yfWREd4HVpbleouHJxX5uM4XSbYzc5mKrRMKxbmXTQ39M-EvRLVW_QekKzubV4tkz8RM",
        "parentId": "root",
        "type": "NOTE",
        "timestamps": {
          "kind": "notes#timestamps",
          "created": "2023-03-12T10:30:50.724Z",
          "updated": "2023-03-12T10:30:50.724Z",
          "trashed": "1970-01-01T00:00:00.000Z",
          "userEdited": "2023-03-12T10:30:50.724Z",
          "recentSharedChangesSeen": "1970-01-01T00:00:00.000Z"
        },
        "title": "",
        "nodeSettings": {
          "newListItemPlacement": "BOTTOM",
          "checkedListItemsPolicy": "GRAVEYARD",
          "graveyardState": "EXPANDED"
        },
        "isArchived": false,
        "isPinned": false,
        "color": "DEFAULT",
        "sortValue": "18874368",
        "annotationsGroup": {
          "kind": "notes#annotationsGroup"
        },
        "lastModifierEmail": "",
        "moved": "1",
        "xplatModel": false
      },
      {
        "kind": "notes#node",
        "id": "186d561227c.be61f6add0ab6005",
        "serverId": "1hgov9FgqmCozFCFFowmCUqYCWlqUsePpVn_wIzk3uOUxNvZf5Kq9wL2xyhEibbCbOl83",
        "parentId": "186d5611e9d.aa4b2bf13f1502bf",
        "parentServerId": "1yfWREd4HVpbleouHJxX5uM4XSbYzc5mKrRMKxbmXTQ39M-EvRLVW_QekKzubV4tkz8RM",
        "type": "LIST_ITEM",
        "timestamps": {
          "kind": "notes#timestamps",
          "created": "2023-03-12T10:30:50.724Z",
          "updated": "2023-03-12T10:30:50.724Z"
        },
        "text": "",
        "checked": false,
        "baseVersion": "0",
        "nodeSettings": {
          "newListItemPlacement": "BOTTOM",
          "checkedListItemsPolicy": "GRAVEYARD",
          "graveyardState": "EXPANDED"
        },
        "sortValue": "0",
        "annotationsGroup": {
          "kind": "notes#annotationsGroup"
        }
      },
      {
        "kind": "notes#node",
        "id": "186d56122aa.a3c4fed397538662",
        "serverId": "15QTk2SuERZTotSzDsEVW5EGusp2r-BGT9ojx7SjzQeA8cpquGrXRa8x84qaWDC2b602L",
        "parentId": "186d5611e9d.aa4b2bf13f1502bf",
        "parentServerId": "1yfWREd4HVpbleouHJxX5uM4XSbYzc5mKrRMKxbmXTQ39M-EvRLVW_QekKzubV4tkz8RM",
        "type": "BLOB",
        "timestamps": {
          "kind": "notes#timestamps",
          "created": "2023-03-12T10:30:50.724Z",
          "updated": "2023-03-12T10:30:50.724Z"
        },
        "blob": {
          "kind": "notes#blob",
          "type": "IMAGE",
          "mimetype": "image/png"
        },
        "nodeSettings": {
          "newListItemPlacement": "BOTTOM",
          "checkedListItemsPolicy": "GRAVEYARD",
          "graveyardState": "EXPANDED"
        },
        "annotationsGroup": {
          "kind": "notes#annotationsGroup"
        }
      }
    ],
    "truncated": false,
    "forceFullResync": false,
    "responseHeader": {
      "updateState": "UTD",
      "requestId": ""
    }
}
```

### Second request
```http request
POST https://notes-pa.googleapis.com/notes/v1/changes
```

*Body:*
```json
{
    "clientTimestamp": "2023-03-12T10:30:51.263Z",
    "nodes": [],
    "requestHeader": {/* default requestHeader */},
    "targetVersion": ""
}
```

*Returns:* 
```json
{
    "kind": "notes#downSync",
    "fromVersion": "AILBLhksRMMth1KwypSg5etwbyb5LmQGpEwfcwgwnb+rRXpzJ9Z43YcjiBOuc167CcyxvgMw8owaRDFJ0OkSLisuRSW6P0OVS2e/DjYBWUgXtyw1j1d3lIu4bRkX6w==",
    "toVersion": "AILBLhlyJPbAajkdKFcli6DL8RO7IwgXYhFFzk2qTFQBuAJtVLv4SKI/i1ivYrA5lFa0cwan4BD3JF/qFQpw2e3qmkwwmWpremrGpjH9OR3t4a52j1h/8v0z42+fTw==",
    "nodes": [
      {
        "kind": "notes#node",
        "id": "186d5611e9d.aa4b2bf13f1502bf",
        "serverId": "1yfWREd4HVpbleouHJxX5uM4XSbYzc5mKrRMKxbmXTQ39M-EvRLVW_QekKzubV4tkz8RM",
        "parentId": "root",
        "type": "NOTE",
        "timestamps": {
          "kind": "notes#timestamps",
          "created": "2023-03-12T10:30:50.724Z",
          "updated": "2023-03-12T10:30:54.213Z",
          "trashed": "1970-01-01T00:00:00.000Z",
          "userEdited": "2023-03-12T10:30:54.213Z",
          "recentSharedChangesSeen": "1970-01-01T00:00:00.000Z"
        },
        "title": "",
        "nodeSettings": {
          "newListItemPlacement": "BOTTOM",
          "checkedListItemsPolicy": "GRAVEYARD",
          "graveyardState": "EXPANDED"
        },
        "isArchived": false,
        "isPinned": false,
        "color": "DEFAULT",
        "sortValue": "18874368",
        "shareState": "ACCEPTED",
        "annotationsGroup": {
          "kind": "notes#annotationsGroup"
        },
        "lastModifierEmail": "",
        "moved": "1",
        "xplatModel": false
      },
      {
        "kind": "notes#node",
        "id": "186d56122aa.a3c4fed397538662",
        "serverId": "15QTk2SuERZTotSzDsEVW5EGusp2r-BGT9ojx7SjzQeA8cpquGrXRa8x84qaWDC2b602L",
        "parentId": "186d5611e9d.aa4b2bf13f1502bf",
        "parentServerId": "1yfWREd4HVpbleouHJxX5uM4XSbYzc5mKrRMKxbmXTQ39M-EvRLVW_QekKzubV4tkz8RM",
        "type": "BLOB",
        "timestamps": {
          "kind": "notes#timestamps",
          "created": "2023-03-12T10:30:50.724Z",
          "updated": "2023-03-12T10:30:54.213Z"
        },
        "blob": {
          "kind": "notes#blob",
          "type": "IMAGE",
          "mimetype": "image/png",
          "byte_size": 36209,
          "width": 1024,
          "height": 512,
          "extracted_text": "ANDROID\n14",
          "extraction_status": "SUCCEEDED",
          "is_uploaded": true
        },
        "nodeSettings": {
          "newListItemPlacement": "BOTTOM",
          "checkedListItemsPolicy": "GRAVEYARD",
          "graveyardState": "EXPANDED"
        },
        "annotationsGroup": {
          "kind": "notes#annotationsGroup"
        }
      }
    ],
    "truncated": false,
    "forceFullResync": false,
    "responseHeader": {
      "updateState": "UTD",
      "requestId": ""
    }
}
```

### Third request

```http request
GET https://keep.google.com/media/v2/{serverId-of-note}/{serverId-of-blob}
```

*with parameters:*

- `sz` - **2148**
- `accept` - **audio/3gpp,audio/amr-wb,image/gif,image/jpg,image/jpeg,image/png**

*Body:* **empty body**

*Returns:* 
**302 FOUND**
and `Location` header where to get the image

### Firth request
*see uri in the `Location` header of previous request*
```http request
GET https://lh3.googleusercontent.com/keep-bbsk/
```

*Returns:* **image in the `HEX` format**
