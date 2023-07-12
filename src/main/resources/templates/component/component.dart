import 'package:flying_redux/flying_redux.dart';
import 'package:flutter/material.dart' hide Action;

import 'action.dart';
import 'effect.dart';
import 'reducer.dart';
import 'state.dart';

class $nameComponent extends Component<$nameState> {
  $nameComponent()
      : super(
          effect: buildEffect(),
          reducer: buildReducer(),
          view: ($nameState state, Dispatch dispatch,
              ComponentContext<$nameState> ctx) {
            return Container(
              padding: const EdgeInsets.all(8.0),
              child: GestureDetector(
                child: Column(
                  children: <Widget>[
                    GestureDetector(
                      child: Container(
                        height: 36.0,
                        color: Colors.green,
                        alignment: AlignmentDirectional.centerStart,
                        child: Row(
                          children: <Widget>[
                            Container(
                              margin: const EdgeInsets.all(8.0),
                              child: const Icon(Icons.label_outline),
                            ),
                            Expanded(
                                child: Text(
                              state.uniqueId ?? '',
                              maxLines: 1,
                              style: const TextStyle(
                                  color: Colors.white, fontSize: 18.0),
                            )),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                onLongPress: () {
                  dispatch($nameActionCreator.onSomeAction(state.uniqueId));
                },
              ),
            );
          },
        );
}
