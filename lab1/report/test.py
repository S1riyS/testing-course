def cleanup_features(list_features: str) -> str:
    return list_features.lstrip("(").rstrip(")")

def parse_list_features(list_features):
    """
    >>> parse_list_features('0:2-6:Age:Flnwgt:Sbsp-LastFeature:10-12')
    [0, 2, 3, 4, 5, 6, 'Age', 'Flnwgt', 'Sbsp-LastFeature', 10, 11, 12]
    """
    if list_features is None:
        return None
    ids = list_features.split(",")
    features = []
    for id in ids:
        try:
            id = [int(id)]
        except ValueError:
            interval = id.split("-")
            if len(interval) == 2:
                left, right = interval[0], interval[1]
                try:
                    left = int(left)
                    right = int(right)
                    id = list(range(left, right + 1))
                except ValueError:
                    id = [id]
            else:
                id = [id]
        features += id
    return features

if __name__ == "__main__":
    # features = cleanup_features('0,3 ,5  ,7,8,10,11,13,14,15,17,18,19,20,21,22,23,24,27,30,31,35,36,37,38,39,40,43,44,46,47,48,49,50,51,52,53,55,56,57,58,59,60,61,62,63,65,66,67,68,69,123,124,125,126,127,128,129,130,131,251,252,254,256,257,258,259,260,262,264,265,266,267,269,270')
    features = cleanup_features('0:2-6:Age:Flnwgt:Sbsp-LastFeature:10-12')
    result = parse_list_features(features)
    print(result)